const API_URL = 'http://localhost:8080';

// ==========================================================================
// 1. FUNÇÕES UTILITÁRIAS (Menu, Forms, API)
// ==========================================================================

function toggleForm(formId, show) {
    // Compatibilidade com chamadas antigas (só boolean)
    if (typeof formId === 'boolean') {
        show = formId;
        formId = 'form-section';
    }

    const form = document.getElementById(formId);
    const buttons = document.getElementById('action-buttons');

    // 1. Esconde todos os formulários ativos
    document.querySelectorAll('.form-container').forEach(f => f.classList.remove('active'));
    
    if (show) {
        // MODO FORMULÁRIO
        if(form) form.classList.add('active');
        if(buttons) buttons.style.display = 'none'; // Esconde botões de topo
        
        // Esconde as listas (tabelas)
        const lists = document.querySelectorAll('[id$="list-section"]'); 
        lists.forEach(l => l.style.display = 'none');
        
        // VERIFICAÇÃO DE "NOVO CADASTRO"
        // Se abriu o form e o campo de ID está vazio, é um cadastro novo.
        // Precisamos destravar campos que a edição pode ter bloqueado (ex: Dono do Pet).
        const editIdInput = document.querySelector(`#${formId} #edit-id`);
        if (!editIdInput || !editIdInput.value) {
           const disabledInputs = form.querySelectorAll('select:disabled, input:disabled');
           disabledInputs.forEach(el => el.disabled = false);
        }

    } else {
        // MODO LISTA
        const lists = document.querySelectorAll('[id$="list-section"]'); 
        lists.forEach(l => l.style.display = 'block');
        if(buttons) buttons.style.display = 'flex'; // Volta botões

        // Limpa os campos do formulário
        const formEl = document.querySelector(`#${formId} form`);
        if(formEl) formEl.reset();
        
        // Limpa IDs ocultos
        const inputsId = document.querySelectorAll('input[type="hidden"]');
        inputsId.forEach(i => i.value = '');
    }
}

async function fetchAPI(endpoint) {
    try {
        const res = await fetch(`${API_URL}${endpoint}`);
        if (!res.ok) throw new Error(`Erro HTTP: ${res.status}`);
        return await res.json();
    } catch (err) {
        console.error("Erro API:", err);
        return [];
    }
}

// Busca item na lista local (já que a API não tem endpoints de getById individuais em alguns controllers)
async function findItemById(endpoint, id) {
    const list = await fetchAPI(endpoint);
    return list.find(item => String(item.id) === String(id)); 
}

async function sendData(endpoint, data, method = 'POST') {
    try {
        console.log(`Enviando [${method}] para ${endpoint}`, data); // Debug

        const res = await fetch(`${API_URL}${endpoint}`, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (res.ok) {
            alert("Salvo com sucesso!");
            location.reload();
        } else {
            // Tratamento de erro robusto
            let erroTexto = "Erro desconhecido";
            try {
                const erroJson = await res.json();
                erroTexto = erroJson.message || JSON.stringify(erroJson);
            } catch {
                erroTexto = await res.text();
            }
            alert(`Erro no servidor (${res.status}): ${erroTexto}`);
        }
    } catch (err) {
        console.error(err);
        alert("Erro de Conexão: Verifique se o servidor está rodando.");
    }
}

async function deleteItem(endpoint) {
    if (confirm("Tem certeza? Isso apagará permanentemente o registro e seus vínculos.")) {
        try {
            const res = await fetch(`${API_URL}${endpoint}`, { method: 'DELETE' });
            if (res.ok) {
                alert("Excluído!");
                location.reload();
            } else {
                alert("Não foi possível excluir. Verifique dependências.");
            }
        } catch (err) {
            alert("Erro de conexão ao excluir.");
        }
    }
}

// ==========================================================================
// 2. LÓGICA ESPECÍFICA POR PÁGINA
// ==========================================================================

// --- DASHBOARD (index.html) ---
if (document.getElementById('dashboard-page')) {
    Promise.all([fetchAPI('/usuario'), fetchAPI('/pet'), fetchAPI('/agendamento')])
    .then(([users, pets, agenda]) => {
        document.getElementById('total-clientes').innerText = users.length;
        document.getElementById('total-pets').innerText = pets.length;
        
        // Filtra agendamentos de hoje
        const hoje = new Date().toISOString().split('T')[0];
        const agendaHoje = agenda.filter(a => a.dataHora && a.dataHora.startsWith(hoje)).length;
        
        document.getElementById('agenda-hoje').innerText = agendaHoje;
        document.getElementById('total-agenda').innerText = agenda.length;
    });
}

// --- CLIENTES (clientes.html) ---
if (document.getElementById('clientes-page')) {
    const tbody = document.getElementById('table-body');
    
    // 1. Carregar Tabela
    fetchAPI('/usuario').then(users => {
        tbody.innerHTML = users.map(u => `
            <tr>
                <td><i class="fas fa-user"></i> ${u.nome}</td>
                <td>${u.email}</td>
                <td>${u.telefone || '-'}</td>
                <td>
                    <button class="btn btn-warning btn-sm" onclick="editarCliente(${u.id})"><i class="fas fa-edit"></i></button>
                    <button class="btn btn-danger btn-sm" onclick="deleteItem('/usuario/${u.id}')"><i class="fas fa-trash"></i></button>
                </td>
            </tr>
        `).join('');
    });

    // 2. Salvar (Create ou Update)
    document.getElementById('main-form').addEventListener('submit', (e) => {
        e.preventDefault();
        const id = document.getElementById('edit-id').value;
        const data = {
            nome: document.getElementById('nome').value,
            email: document.getElementById('email').value,
            telefone: document.getElementById('telefone').value
        };
        
        // Usuário usa PUT para atualizar, POST para criar
        sendData(id ? `/usuario/${id}` : '/usuario', data, id ? 'PUT' : 'POST');
    });

    // 3. Preencher Form de Edição
    window.editarCliente = async (id) => {
        const u = await findItemById('/usuario', id);
        if(u) {
            document.getElementById('edit-id').value = u.id;
            document.getElementById('nome').value = u.nome;
            document.getElementById('email').value = u.email;
            document.getElementById('telefone').value = u.telefone || '';
            toggleForm('form-section', true);
        }
    };
}

// --- PETS (pets.html) ---
if (document.getElementById('pets-page')) {
    
    // 1. Carregar Donos no Select
    fetchAPI('/usuario').then(users => {
        const select = document.getElementById('usuarioId');
        select.innerHTML = '<option value="">Selecione...</option>';
        users.forEach(u => select.innerHTML += `<option value="${u.id}">${u.nome}</option>`);
    });

    // 2. Carregar Tabela
    fetchAPI('/pet').then(pets => {
        const icons = { 'CACHORRO': 'fa-dog', 'GATO': 'fa-cat', 'AVE': 'fa-crow', 'ROEDOR': 'fa-carrot', 'OUTRO': 'fa-paw' };
        document.getElementById('table-body').innerHTML = pets.map(p => `
            <tr>
                <td><i class="fas ${icons[p.tipo] || 'fa-paw'}"></i> ${p.nome}</td>
                <td>${p.tipo}</td>
                <td>${p.raca}</td>
                <td><i class="fas fa-user-circle"></i> ${p.usuario ? p.usuario.nome : '-'}</td>
                <td>
                    <button class="btn btn-warning btn-sm" onclick="editarPet(${p.id})"><i class="fas fa-edit"></i></button>
                    <button class="btn btn-danger btn-sm" onclick="deleteItem('/pet/${p.id}')"><i class="fas fa-trash"></i></button>
                </td>
            </tr>
        `).join('');
    });

    // 3. Salvar (Lógica Especial de Dono)
    document.getElementById('main-form').addEventListener('submit', (e) => {
        e.preventDefault();
        const id = document.getElementById('edit-id').value;
        
        let data = {
            nome: document.getElementById('nome').value,
            tipo: document.getElementById('tipo').value.toUpperCase(),
            raca: document.getElementById('raca').value,
            idade: document.getElementById('idade').value,
            peso: document.getElementById('peso').value
        };

        // Se for NOVO (POST), mandamos o usuarioId para o PetCreateDTO
        if (!id) {
            data.usuarioId = document.getElementById('usuarioId').value;
        }
        // Se for EDIÇÃO (PATCH), NÃO mandamos o usuário, pois o campo está bloqueado

        sendData(id ? `/pet/${id}` : '/pet', data, id ? 'PATCH' : 'POST');
    });

    // 4. Editar
    window.editarPet = async (id) => {
        const p = await findItemById('/pet', id);
        if(p) {
            document.getElementById('edit-id').value = p.id;
            document.getElementById('nome').value = p.nome;
            
            // Tenta selecionar o tipo corretamente (ignora case)
            let tipoSelect = document.getElementById('tipo');
            tipoSelect.value = p.tipo; 
            if(!tipoSelect.value) tipoSelect.value = p.tipo.toUpperCase(); 

            document.getElementById('raca').value = p.raca;
            document.getElementById('idade').value = p.idade;
            document.getElementById('peso').value = p.peso;
            
            // Preenche e BLOQUEIA o dono
            if(p.usuario) document.getElementById('usuarioId').value = p.usuario.id;
            document.getElementById('usuarioId').disabled = true; 
            
            toggleForm('form-section', true);
        }
    };
}

// --- SERVIÇOS E CATEGORIAS (servicos.html) ---
if (document.getElementById('servicos-page')) {
    
    // Carrega dropdown e tabela de categorias
    function carregarTipos() {
        const select = document.getElementById('tipoServicoId');
        const tbodyCat = document.getElementById('cat-table-body');
        
        if(select) select.innerHTML = '<option value="">Selecione...</option>';
        if(tbodyCat) tbodyCat.innerHTML = '';

        fetchAPI('/tipo-servico').then(tipos => {
            // Select do Form de Serviço
            if(select) tipos.forEach(t => select.innerHTML += `<option value="${t.id}">${t.nome}</option>`);

            // Tabela de Gerenciamento de Categorias
            if(tbodyCat) {
                tbodyCat.innerHTML = tipos.map(t => `
                    <tr>
                        <td>${t.id}</td>
                        <td>${t.nome}</td>
                        <td>${t.descricao || '-'}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" onclick="editarCategoria(${t.id})"><i class="fas fa-edit"></i></button>
                            <button class="btn btn-danger btn-sm" onclick="deleteItem('/tipo-servico/${t.id}')"><i class="fas fa-trash"></i></button>
                        </td>
                    </tr>
                `).join('');
            }
        });
    }
    carregarTipos();

    // Carrega Tabela de Serviços
    fetchAPI('/servico').then(servicos => {
        document.getElementById('table-body').innerHTML = servicos.map(s => `
            <tr>
                <td><i class="fas fa-concierge-bell"></i> ${s.nome}</td>
                <td>${s.nomeTipoServico || (s.tipoServico ? s.tipoServico.nome : '-')}</td>
                <td>R$ ${s.preco}</td>
                <td><i class="far fa-clock"></i> ${s.duracaoMinutos} min</td>
                <td>
                    <button class="btn btn-warning btn-sm" onclick="editarServico(${s.id})"><i class="fas fa-edit"></i></button>
                    <button class="btn btn-danger btn-sm" onclick="deleteItem('/servico/${s.id}')"><i class="fas fa-trash"></i></button>
                </td>
            </tr>
        `).join('');
    });

    // Salvar Serviço
    document.getElementById('main-form').addEventListener('submit', (e) => {
        e.preventDefault();
        const id = document.getElementById('edit-id').value;
        const data = {
            nome: document.getElementById('nome').value,
            descricao: document.getElementById('descricao').value,
            preco: document.getElementById('preco').value,
            duracaoMinutos: document.getElementById('duracao').value,
            tipoServicoId: document.getElementById('tipoServicoId').value // Envia só o ID
        };
        
        // Serviço usa PUT
        sendData(id ? `/servico/${id}` : '/servico', data, id ? 'PUT' : 'POST');
    });

    // Salvar Categoria
    document.getElementById('categoria-form').addEventListener('submit', (e) => {
        e.preventDefault();
        const id = document.getElementById('cat-edit-id').value;
        const data = {
            nome: document.getElementById('cat-nome').value,
            descricao: document.getElementById('cat-desc').value
        };
        // Categoria usa PUT
        sendData(id ? `/tipo-servico/${id}` : '/tipo-servico', data, id ? 'PUT' : 'POST');
    });

    // Editar Funções
    window.editarServico = async (id) => {
        const s = await findItemById('/servico', id);
        if(s) {
            document.getElementById('edit-id').value = s.id;
            document.getElementById('nome').value = s.nome;
            document.getElementById('descricao').value = s.descricao || '';
            document.getElementById('preco').value = s.preco;
            document.getElementById('duracao').value = s.duracaoMinutos;
            
            // Tenta preencher categoria (pelo objeto ou pelo ID direto)
            if(s.tipoServicoId) document.getElementById('tipoServicoId').value = s.tipoServicoId;
            else if(s.tipoServico) document.getElementById('tipoServicoId').value = s.tipoServico.id;

            toggleForm('form-section', true);
        }
    };

    window.editarCategoria = async (id) => {
        const c = await findItemById('/tipo-servico', id);
        if(c) {
            document.getElementById('cat-edit-id').value = c.id;
            document.getElementById('cat-nome').value = c.nome;
            document.getElementById('cat-desc').value = c.descricao || '';
            toggleForm('category-form-section', true);
        }
    }
}

// --- AGENDA (agenda.html) ---
if (document.getElementById('agenda-page')) {
    
    // Preenche selects de Pet e Serviço
    Promise.all([fetchAPI('/pet'), fetchAPI('/servico')]).then(([pets, servicos]) => {
        const selPet = document.getElementById('petId');
        const selServ = document.getElementById('servicoId');
        
        pets.forEach(p => selPet.innerHTML += `<option value="${p.id}">${p.nome} (${p.usuario ? p.usuario.nome : 'Cliente'})</option>`);
        servicos.forEach(s => selServ.innerHTML += `<option value="${s.id}">${s.nome} - R$${s.preco}</option>`);
    });

    // Tabela Agenda
    fetchAPI('/agendamento').then(agendas => {
        document.getElementById('table-body').innerHTML = agendas.map(a => `
            <tr>
                <td><i class="far fa-calendar-alt"></i> ${new Date(a.dataHora).toLocaleString('pt-BR')}</td>
                <td><i class="fas fa-paw"></i> ${a.nomePet}</td>
                <td>${a.nomeServico}</td>
                <td><span class="status status-${a.status}">${a.status}</span></td>
                <td>
                    <button title="Editar" class="btn btn-warning btn-sm" onclick="editarAgendamento(${a.id})"><i class="fas fa-edit"></i></button>
                    <button title="Confirmar" class="btn btn-primary btn-sm" onclick="mudarStatus(${a.id}, 'CONFIRMADO')"><i class="fas fa-check"></i></button>
                    <button title="Concluir" class="btn btn-secondary btn-sm" onclick="mudarStatus(${a.id}, 'CONCLUIDO')"><i class="fas fa-check-double"></i></button>
                    <button title="Cancelar" class="btn btn-danger btn-sm" onclick="mudarStatus(${a.id}, 'CANCELADO')"><i class="fas fa-times"></i></button>
                </td>
            </tr>
        `).join('');
    });

    // Salvar Agendamento
    document.getElementById('main-form').addEventListener('submit', (e) => {
        e.preventDefault();
        const id = document.getElementById('edit-id').value;
        const dataStr = document.getElementById('data').value;
        const horaStr = document.getElementById('hora').value;

        let payload = {};
        let url = id ? `/agendamento/${id}` : '/agendamento';
        let method = id ? 'PUT' : 'POST'; // Agendamento usa PUT para update

        if(id) {
            // EDIÇÃO: Manda DTO de Update (sem pet/servico, só data/obs)
            payload = {
                dataHora: `${dataStr}T${horaStr}:00`,
                observacoes: document.getElementById('obs').value
            };
        } else {
            // CRIAÇÃO: Manda DTO de Criação completo
            payload = {
                petId: document.getElementById('petId').value,
                servicoId: document.getElementById('servicoId').value,
                dataHora: `${dataStr}T${horaStr}:00`,
                observacoes: document.getElementById('obs').value
            };
        }
        sendData(url, payload, method);
    });

    // Mudar Status (PUT)
    window.mudarStatus = (id, status) => sendData(`/agendamento/${id}`, { status: status }, 'PUT');

    // Editar
    window.editarAgendamento = async (id) => {
        const a = await findItemById('/agendamento', id);
        if(a) {
            document.getElementById('edit-id').value = a.id;
            
            // Preenche e TRAVA Pet e Serviço
            document.getElementById('petId').value = a.petId;
            document.getElementById('petId').disabled = true; 
            
            document.getElementById('servicoId').value = a.servicoId;
            document.getElementById('servicoId').disabled = true;

            // Formata data e hora para o input
            const dataHora = a.dataHora; 
            if(dataHora) {
                document.getElementById('data').value = dataHora.split('T')[0];
                document.getElementById('hora').value = dataHora.split('T')[1].substring(0,5);
            }
            document.getElementById('obs').value = a.observacoes || '';
            
            toggleForm('form-section', true);
        }
    };
}
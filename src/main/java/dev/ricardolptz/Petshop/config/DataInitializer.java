package dev.ricardolptz.Petshop.config;

import dev.ricardolptz.Petshop.model.TipoPet;
import dev.ricardolptz.Petshop.repository.TipoPetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TipoPetRepository tipoPetRepository;

    public DataInitializer(TipoPetRepository tipoPetRepository) {
        this.tipoPetRepository = tipoPetRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (tipoPetRepository.count() == 0) {

            System.out.println("Populando a tabela 'tipo_animal' com dados iniciais...");

            List<TipoPet> tiposIniciais = Arrays.stream(TipoPet.Tipo.values())
                    .map(tipoEnum -> {
                        TipoPet novoTipo = new TipoPet();
                        novoTipo.setTipo(tipoEnum);
                        return novoTipo;
                    })
                    .collect(Collectors.toList());

            tipoPetRepository.saveAll(tiposIniciais);

            System.out.println("Tabela 'tipo_animal' populada com " + tiposIniciais.size() + " registros.");
        } else {
            System.out.println("Tabela 'tipo_animal' já contém dados. Ignorando o populamento.");
        }
    }
}
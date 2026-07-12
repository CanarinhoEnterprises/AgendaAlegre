package br.ufes.reserva_espacos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.equipamentodto.CadastroEquipamentoDTO;
import br.ufes.reserva_espacos.entity.Equipamento;
import br.ufes.reserva_espacos.entity.Espaco;
import br.ufes.reserva_espacos.repositories.EquipamentoRepository;
import br.ufes.reserva_espacos.repositories.EspacoRepository;

@Service
public class EquipamentoService {
    private final EquipamentoRepository equipamentoRepository;
    private final EspacoRepository espacoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, EspacoRepository espacoRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.espacoRepository = espacoRepository;
    }

    public Equipamento cadastrar(CadastroEquipamentoDTO dto) {
        validarEquipamento(dto);

        Optional<Espaco> espaco = espacoRepository.findById(dto.getIdEspaco());
        if (espaco.isEmpty()) {
            throw new RuntimeException("Espaço não encontrado");
        }

        Equipamento equipamento = new Equipamento();
        equipamento.setEspaco(espaco.get());
        equipamento.setNome(dto.getNome());
        equipamento.setDescricao(dto.getDescricao());
        equipamento.setQtd(dto.getQtd());

        return equipamentoRepository.save(equipamento);
    }

    public List<Equipamento> listar() {
        return equipamentoRepository.findAll();
    }

    public List<Equipamento> listarPorEspaco(Integer idEspaco) {
        return equipamentoRepository.findByEspaco_IdEspacoOrderByNomeAsc(idEspaco);
    }

    public Optional<Equipamento> consultar(Integer id) {
        return equipamentoRepository.findById(id);
    }

    public Equipamento atualizar(Integer id, CadastroEquipamentoDTO dto) {
        validarEquipamento(dto);

        Optional<Equipamento> equipamento = equipamentoRepository.findById(id);
        if (equipamento.isEmpty()) {
            throw new RuntimeException("Equipamento não encontrado");
        }

        Optional<Espaco> espaco = espacoRepository.findById(dto.getIdEspaco());
        if (espaco.isEmpty()) {
            throw new RuntimeException("Espaço não encontrado");
        }

        Equipamento equip = equipamento.get();
        equip.setEspaco(espaco.get());
        equip.setNome(dto.getNome());
        equip.setDescricao(dto.getDescricao());
        equip.setQtd(dto.getQtd());

        return equipamentoRepository.save(equip);
    }

    public void excluir(Integer id) {
        Optional<Equipamento> equipamento = equipamentoRepository.findById(id);
        if (equipamento.isEmpty()) {
            throw new RuntimeException("Equipamento não encontrado");
        }
        equipamentoRepository.deleteById(id);
    }

    private void validarEquipamento(CadastroEquipamentoDTO dto) {
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome do equipamento é obrigatório");
        }
        if (dto.getDescricao() == null || dto.getDescricao().trim().isEmpty()) {
            throw new RuntimeException("Descrição do equipamento é obrigatória");
        }
        if (dto.getQtd() == null || dto.getQtd() < 0) {
            throw new RuntimeException("Quantidade deve ser maior ou igual a 0");
        }
        if (dto.getIdEspaco() == null) {
            throw new RuntimeException("ID do espaço é obrigatório");
        }
    }
}

package br.ufes.reserva_espacos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufes.reserva_espacos.dto.ocorrenciadto.CadastroOcorrenciaDTO;
import br.ufes.reserva_espacos.entity.Espaco;
import br.ufes.reserva_espacos.entity.Ocorrencia;
import br.ufes.reserva_espacos.enums.StatusEspaco;
import br.ufes.reserva_espacos.enums.StatusOcorrencia;
import br.ufes.reserva_espacos.enums.TipoOcorrencia;
import br.ufes.reserva_espacos.repositories.EspacoRepository;
import br.ufes.reserva_espacos.repositories.OcorrenciaRepository;

@Service
public class OcorrenciaService {
    private final OcorrenciaRepository ocorrenciaRepository;
    private final EspacoRepository espacoRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository, EspacoRepository espacoRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.espacoRepository = espacoRepository;
    }

    @Transactional
    public Ocorrencia cadastrar(CadastroOcorrenciaDTO dto) {
        validarOcorrencia(dto);

        Optional<Espaco> espaco = espacoRepository.findById(dto.getIdEspaco());
        if (espaco.isEmpty()) {
            throw new RuntimeException("Espaço não encontrado");
        }

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setTitulo(dto.getTitulo());
        ocorrencia.setDescricao(dto.getDescricao());
        ocorrencia.setDtRegistro(dto.getDtRegistro() != null ? dto.getDtRegistro() : LocalDate.now());
        ocorrencia.setTipo(dto.getTipo());
        ocorrencia.setEspaco(espaco.get());
        ocorrencia.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusOcorrencia.ABERTA);

        Ocorrencia saved = ocorrenciaRepository.save(ocorrencia);

        // RN33: Se for MANUTENCAO, interditar o espaço
        if (dto.getTipo() == TipoOcorrencia.MANUTENCAO) {
            Espaco e = espaco.get();
            e.setStatus(StatusEspaco.INTERDITADO);
            espacoRepository.save(e);
        }

        return saved;
    }

    public List<Ocorrencia> listar() {
        return ocorrenciaRepository.findAllByOrderByDtRegistroDesc();
    }

    public List<Ocorrencia> listarPorEspaco(Integer idEspaco) {
        return ocorrenciaRepository.findByEspaco_IdEspacoOrderByDtRegistroDesc(idEspaco);
    }

    public Optional<Ocorrencia> consultar(Integer id) {
        return ocorrenciaRepository.findById(id);
    }

    @Transactional
    public Ocorrencia atualizar(Integer id, CadastroOcorrenciaDTO dto) {
        validarOcorrencia(dto);

        Optional<Ocorrencia> ocorrencia = ocorrenciaRepository.findById(id);
        if (ocorrencia.isEmpty()) {
            throw new RuntimeException("Ocorrência não encontrada");
        }

        Optional<Espaco> espaco = espacoRepository.findById(dto.getIdEspaco());
        if (espaco.isEmpty()) {
            throw new RuntimeException("Espaço não encontrado");
        }

        Ocorrencia ocor = ocorrencia.get();

        // RN34: ocorrência de manutenção só pode ser encerrada após o espaço
        // ser reativado (ATIVO) ou inativado (INATIVO)
        if (ocor.getTipo() == TipoOcorrencia.MANUTENCAO
                && dto.getStatus() == StatusOcorrencia.RESOLVIDA
                && ocor.getStatus() != StatusOcorrencia.RESOLVIDA) {
            StatusEspaco statusEspaco = espaco.get().getStatus();
            if (statusEspaco != StatusEspaco.ATIVO && statusEspaco != StatusEspaco.INATIVO) {
                throw new RuntimeException(
                        "Ocorrência de manutenção só pode ser encerrada após o espaço ser reativado ou inativado.");
            }
        }

        ocor.setTitulo(dto.getTitulo());
        ocor.setDescricao(dto.getDescricao());
        ocor.setDtRegistro(dto.getDtRegistro());
        ocor.setTipo(dto.getTipo());
        ocor.setEspaco(espaco.get());
        ocor.setStatus(dto.getStatus());

        return ocorrenciaRepository.save(ocor);
    }

    public void excluir(Integer id) {
        Optional<Ocorrencia> ocorrencia = ocorrenciaRepository.findById(id);
        if (ocorrencia.isEmpty()) {
            throw new RuntimeException("Ocorrência não encontrada");
        }
        ocorrenciaRepository.deleteById(id);
    }

    private void validarOcorrencia(CadastroOcorrenciaDTO dto) {
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("Título da ocorrência é obrigatório");
        }
        if (dto.getDescricao() == null || dto.getDescricao().trim().isEmpty()) {
            throw new RuntimeException("Descrição da ocorrência é obrigatória");
        }
        if (dto.getDtRegistro() == null) {
            throw new RuntimeException("Data de registro é obrigatória");
        }
        if (dto.getTipo() == null) {
            throw new RuntimeException("Tipo de ocorrência é obrigatório");
        }
        if (dto.getIdEspaco() == null) {
            throw new RuntimeException("ID do espaço é obrigatório");
        }
    }
}

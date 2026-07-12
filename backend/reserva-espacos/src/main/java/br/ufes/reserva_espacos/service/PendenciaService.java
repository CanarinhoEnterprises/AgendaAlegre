package br.ufes.reserva_espacos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.pendenciadto.CadastroPendenciaDTO;
import br.ufes.reserva_espacos.entity.Administrador;
import br.ufes.reserva_espacos.entity.Pendencia;
import br.ufes.reserva_espacos.entity.Reserva;
import br.ufes.reserva_espacos.entity.TipoDoc;
import br.ufes.reserva_espacos.enums.StatusPendencia;
import br.ufes.reserva_espacos.repositories.AdministradorRepository;
import br.ufes.reserva_espacos.repositories.PendenciaRepository;
import br.ufes.reserva_espacos.repositories.ReservaRepository;
import br.ufes.reserva_espacos.repositories.TipoDocRepository;

@Service
public class PendenciaService {
    private final PendenciaRepository pendenciaRepository;
    private final ReservaRepository reservaRepository;
    private final TipoDocRepository tipoDocRepository;
    private final AdministradorRepository administradorRepository;

    public PendenciaService(PendenciaRepository pendenciaRepository, ReservaRepository reservaRepository,
                            TipoDocRepository tipoDocRepository, AdministradorRepository administradorRepository) {
        this.pendenciaRepository = pendenciaRepository;
        this.reservaRepository = reservaRepository;
        this.tipoDocRepository = tipoDocRepository;
        this.administradorRepository = administradorRepository;
    }

    public Pendencia cadastrar(CadastroPendenciaDTO dto) {
        validarPendencia(dto);

        Optional<Reserva> reserva = reservaRepository.findById(dto.getIdReserva());
        if (reserva.isEmpty()) {
            throw new RuntimeException("Reserva não encontrada");
        }

        Pendencia pendencia = new Pendencia();
        pendencia.setDtCriacao(LocalDateTime.now());
        pendencia.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusPendencia.ABERTA);
        pendencia.setReserva(reserva.get());
        pendencia.setDescricao(dto.getDescricao());
        pendencia.setTipo(dto.getTipo());

        if (dto.getIdTipoDoc() != null) {
            Optional<TipoDoc> tipoDoc = tipoDocRepository.findById(dto.getIdTipoDoc());
            if (tipoDoc.isPresent()) {
                pendencia.setTipoDoc(tipoDoc.get());
            }
        }

        if (dto.getObsUsuario() != null) {
            pendencia.setObsUsuario(dto.getObsUsuario());
        }

        if (dto.getIdAdministrador() != null) {
            Optional<Administrador> admin = administradorRepository.findById(dto.getIdAdministrador());
            if (admin.isPresent()) {
                pendencia.setAdministrador(admin.get());
            }
        }

        return pendenciaRepository.save(pendencia);
    }

    public List<Pendencia> listar() {
        return pendenciaRepository.findAllByOrderByDtCriacaoDesc();
    }

    public List<Pendencia> listarPorReserva(Integer idReserva) {
        return pendenciaRepository.findByReserva_IdReservaOrderByDtCriacaoDesc(idReserva);
    }

    public List<Pendencia> listarPorStatus(StatusPendencia status) {
        return pendenciaRepository.findByStatusOrderByDtCriacaoDesc(status);
    }

    public Optional<Pendencia> consultar(Integer id) {
        return pendenciaRepository.findById(id);
    }

    public Pendencia atualizar(Integer id, CadastroPendenciaDTO dto) {
        validarPendencia(dto);

        Optional<Pendencia> pendencia = pendenciaRepository.findById(id);
        if (pendencia.isEmpty()) {
            throw new RuntimeException("Pendência não encontrada");
        }

        Pendencia pend = pendencia.get();
        pend.setStatus(dto.getStatus());
        pend.setDescricao(dto.getDescricao());
        pend.setTipo(dto.getTipo());
        pend.setObsUsuario(dto.getObsUsuario());

        if (dto.getIdTipoDoc() != null) {
            Optional<TipoDoc> tipoDoc = tipoDocRepository.findById(dto.getIdTipoDoc());
            if (tipoDoc.isPresent()) {
                pend.setTipoDoc(tipoDoc.get());
            }
        }

        if (dto.getIdAdministrador() != null) {
            Optional<Administrador> admin = administradorRepository.findById(dto.getIdAdministrador());
            if (admin.isPresent()) {
                pend.setAdministrador(admin.get());
            }
        }

        // Se status mudou para RESPONDIDA, registra data de resolução
        if (dto.getStatus() == StatusPendencia.RESPONDIDA && pend.getDtResolucao() == null) {
            pend.setDtResolucao(LocalDateTime.now());
        }

        return pendenciaRepository.save(pend);
    }

    public void excluir(Integer id) {
        Optional<Pendencia> pendencia = pendenciaRepository.findById(id);
        if (pendencia.isEmpty()) {
            throw new RuntimeException("Pendência não encontrada");
        }
        pendenciaRepository.deleteById(id);
    }

    private void validarPendencia(CadastroPendenciaDTO dto) {
        if (dto.getIdReserva() == null) {
            throw new RuntimeException("ID da reserva é obrigatório");
        }
        if (dto.getDescricao() == null || dto.getDescricao().trim().isEmpty()) {
            throw new RuntimeException("Descrição da pendência é obrigatória");
        }
        if (dto.getTipo() == null) {
            throw new RuntimeException("Tipo de pendência é obrigatório");
        }
    }
}

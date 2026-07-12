package br.ufes.reserva_espacos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.arquivodto.CadastroArquivoDTO;
import br.ufes.reserva_espacos.entity.Arquivo;
import br.ufes.reserva_espacos.entity.Pendencia;
import br.ufes.reserva_espacos.entity.TipoDoc;
import br.ufes.reserva_espacos.repositories.ArquivoRepository;
import br.ufes.reserva_espacos.repositories.PendenciaRepository;
import br.ufes.reserva_espacos.repositories.TipoDocRepository;

@Service
public class ArquivoService {
    private final ArquivoRepository arquivoRepository;
    private final PendenciaRepository pendenciaRepository;
    private final TipoDocRepository tipoDocRepository;

    public ArquivoService(ArquivoRepository arquivoRepository, PendenciaRepository pendenciaRepository,
                          TipoDocRepository tipoDocRepository) {
        this.arquivoRepository = arquivoRepository;
        this.pendenciaRepository = pendenciaRepository;
        this.tipoDocRepository = tipoDocRepository;
    }

    public Arquivo cadastrar(CadastroArquivoDTO dto) {
        validarArquivo(dto);

        Optional<Pendencia> pendencia = pendenciaRepository.findById(dto.getIdPendencia());
        if (pendencia.isEmpty()) {
            throw new RuntimeException("Pendência não encontrada");
        }

        Optional<TipoDoc> tipoDoc = tipoDocRepository.findById(dto.getIdTipoDoc());
        if (tipoDoc.isEmpty()) {
            throw new RuntimeException("Tipo documento não encontrado");
        }

        Arquivo arquivo = new Arquivo();
        arquivo.setPendencia(pendencia.get());
        arquivo.setTipoDoc(tipoDoc.get());
        arquivo.setNome(dto.getNome());
        arquivo.setUrl(dto.getUrl());
        arquivo.setExtensao(dto.getExtensao());
        arquivo.setTamanho(dto.getTamanho());
        arquivo.setDtUpload(LocalDateTime.now());

        return arquivoRepository.save(arquivo);
    }

    public List<Arquivo> listar() {
        return arquivoRepository.findAll();
    }

    public List<Arquivo> listarPorPendencia(Integer idPendencia) {
        return arquivoRepository.findByPendencia_IdPendenciaOrderByDtUploadDesc(idPendencia);
    }

    public List<Arquivo> listarPorTipoDoc(Integer idTipoDoc) {
        return arquivoRepository.findByTipoDoc_IdTipoDocOrderByDtUploadDesc(idTipoDoc);
    }

    public Optional<Arquivo> consultar(Integer id) {
        return arquivoRepository.findById(id);
    }

    public Arquivo atualizar(Integer id, CadastroArquivoDTO dto) {
        validarArquivo(dto);

        Optional<Arquivo> arquivo = arquivoRepository.findById(id);
        if (arquivo.isEmpty()) {
            throw new RuntimeException("Arquivo não encontrado");
        }

        Optional<Pendencia> pendencia = pendenciaRepository.findById(dto.getIdPendencia());
        if (pendencia.isEmpty()) {
            throw new RuntimeException("Pendência não encontrada");
        }

        Optional<TipoDoc> tipoDoc = tipoDocRepository.findById(dto.getIdTipoDoc());
        if (tipoDoc.isEmpty()) {
            throw new RuntimeException("Tipo documento não encontrado");
        }

        Arquivo arq = arquivo.get();
        arq.setPendencia(pendencia.get());
        arq.setTipoDoc(tipoDoc.get());
        arq.setNome(dto.getNome());
        arq.setUrl(dto.getUrl());
        arq.setExtensao(dto.getExtensao());
        arq.setTamanho(dto.getTamanho());

        return arquivoRepository.save(arq);
    }

    public void excluir(Integer id) {
        Optional<Arquivo> arquivo = arquivoRepository.findById(id);
        if (arquivo.isEmpty()) {
            throw new RuntimeException("Arquivo não encontrado");
        }
        arquivoRepository.deleteById(id);
    }

    private void validarArquivo(CadastroArquivoDTO dto) {
        if (dto.getIdPendencia() == null) {
            throw new RuntimeException("ID da pendência é obrigatório");
        }
        if (dto.getIdTipoDoc() == null) {
            throw new RuntimeException("ID do tipo documento é obrigatório");
        }
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome do arquivo é obrigatório");
        }
        if (dto.getUrl() == null || dto.getUrl().trim().isEmpty()) {
            throw new RuntimeException("URL do arquivo é obrigatória");
        }
        if (dto.getExtensao() == null || dto.getExtensao().trim().isEmpty()) {
            throw new RuntimeException("Extensão do arquivo é obrigatória");
        }
        if (dto.getTamanho() == null || dto.getTamanho() <= 0) {
            throw new RuntimeException("Tamanho do arquivo deve ser maior que 0");
        }
    }
}

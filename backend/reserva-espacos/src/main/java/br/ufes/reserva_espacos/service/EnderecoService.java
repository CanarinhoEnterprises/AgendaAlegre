package br.ufes.reserva_espacos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.enderecodto.EnderecoRequestDTO;
import br.ufes.reserva_espacos.dto.enderecodto.EnderecoResponseDTO;
import br.ufes.reserva_espacos.entity.Cidade;
import br.ufes.reserva_espacos.entity.Endereco;
import br.ufes.reserva_espacos.repositories.CidadeRepository;
import br.ufes.reserva_espacos.repositories.EnderecoRepository;
import jakarta.transaction.Transactional;

@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final CidadeRepository cidadeRepository;
    
    public EnderecoService(EnderecoRepository enderecoRepository, CidadeRepository cidadeRepository) {
        this.enderecoRepository = enderecoRepository;
        this.cidadeRepository = cidadeRepository;
    }

    @Transactional
    public Endereco cadastrar(EnderecoRequestDTO dto) {
        Cidade cidade = cidadeRepository.findById(dto.getIdCidade())
            .orElseThrow(() -> new RuntimeException("Cidade não encontrada."));

        Endereco endereco = new Endereco();
        endereco.setCidade(cidade);
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setBairro(dto.getBairro());
        endereco.setCep(dto.getCep());
        endereco.setNum(dto.getNum());
        endereco.setComp(dto.getComp());
        endereco.setReferencia(dto.getReferencia());

        return enderecoRepository.save(endereco);
    }

    public List<EnderecoResponseDTO> listarTodos() {
        return enderecoRepository.findAll()
            .stream()
            .map(EnderecoResponseDTO::from)
            .toList();
    }

    public Endereco buscaPorId(Integer id) {
        return enderecoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Endereço não encontrado."));
    }

    @Transactional
    public void excluir(Integer id) {
        if (!enderecoRepository.existsById(id)) {
            throw new RuntimeException("Endereço não encontrado.");
        }
        enderecoRepository.deleteById(id);
    }

    @Transactional
    public Endereco atualizar(Integer id, EnderecoRequestDTO dados) {
        Endereco endereco = buscaPorId(id);

        if (!endereco.getCidade().getIdCidade().equals(dados.getIdCidade())) {
            Cidade novaCidade = cidadeRepository.findById(dados.getIdCidade())
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada."));
            endereco.setCidade(novaCidade);
        }

        endereco.setLogradouro(dados.getLogradouro());
        endereco.setBairro(dados.getBairro());
        endereco.setCep(dados.getCep());
        endereco.setNum(dados.getNum());
        endereco.setComp(dados.getComp());
        endereco.setReferencia(dados.getReferencia());

        return enderecoRepository.save(endereco);
    }
}
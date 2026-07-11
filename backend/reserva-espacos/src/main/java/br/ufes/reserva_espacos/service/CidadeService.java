package br.ufes.reserva_espacos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.CidadeDropdownDTO;
import br.ufes.reserva_espacos.dto.CidadeRequestDTO;
import br.ufes.reserva_espacos.dto.CidadeResponseDTO;
import br.ufes.reserva_espacos.entity.Cidade;
import br.ufes.reserva_espacos.repositories.CidadeRepository;
import jakarta.transaction.Transactional;

@Service
public class CidadeService {
    private final CidadeRepository cidadeRepository;
    
    public CidadeService(CidadeRepository cidadeRepository){
        this.cidadeRepository = cidadeRepository;
    }

    @Transactional
    public Cidade cadastrar(CidadeRequestDTO dto){
        if (cidadeRepository.existsByNomeAndUF(dto.getNome(),dto.getUF())){
            throw new RuntimeException("Cidade já cadastrada.");
        }

        Cidade cidade = new Cidade();

        cidade.setNome(dto.getNome());
        cidade.setUF(dto.getUF());

        cidade = cidadeRepository.save(cidade);

        return cidade;
    }

    public List<CidadeResponseDTO> listarUF_Nome(){
        return cidadeRepository.findAllByOrderByUFAscNomeAsc()
        .stream()
        .map(CidadeResponseDTO::from)
        .toList();
    }

    public List<CidadeDropdownDTO> listarCombo(){
        return cidadeRepository.findAllByOrderByUFAscNomeAsc()
        .stream()
        .map(CidadeDropdownDTO::from)
        .toList();
    }

    public Cidade buscaPorId(Integer id){
        return cidadeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Cidade não encontrada."));
    }

    public void excluir(Integer id){
        if(!cidadeRepository.existsById(id)){
            throw new RuntimeException("Cidade não encontrada.");
        }
        cidadeRepository.deleteById(id);
    }

    public Cidade atualizar(Integer id, Cidade dados){
        
        Cidade cidade = buscaPorId(id);

        if (!cidade.getUF().equals(dados.getUF()) || !cidade.getNome().equals(dados.getNome()) && cidadeRepository.existsByNomeAndUF(dados.getNome(),dados.getUF())){
            throw new RuntimeException("Cidade já cadastrada.");
        }

        cidade.setNome(dados.getNome());
        cidade.setUF(dados.getUF());

        return cidadeRepository.save(cidade);
    }
}

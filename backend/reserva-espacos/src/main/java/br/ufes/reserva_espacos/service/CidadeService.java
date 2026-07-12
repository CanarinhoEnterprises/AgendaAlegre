package br.ufes.reserva_espacos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.cidadedto.CidadeDropdownDTO;
import br.ufes.reserva_espacos.dto.cidadedto.CidadeRequestDTO;
import br.ufes.reserva_espacos.dto.cidadedto.CidadeResponseDTO;
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
    public CidadeResponseDTO cadastrar(CidadeRequestDTO dto){
        if (cidadeRepository.existsByNomeAndUF(dto.getNome(),dto.getUF())){
            throw new RuntimeException("Cidade já cadastrada.");
        }

        Cidade cidade = new Cidade();

        cidade.setNome(dto.getNome());
        cidade.setUF(dto.getUF());

        cidade = cidadeRepository.save(cidade);

        return CidadeResponseDTO.from(cidade);
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

    public CidadeResponseDTO buscaPorIdGET(Integer id){
        return CidadeResponseDTO.from(buscaPorId(id));
    }

    public void excluir(Integer id){
        if(!cidadeRepository.existsById(id)){
            throw new RuntimeException("Cidade não encontrada.");
        }
        cidadeRepository.deleteById(id);
    }

    public CidadeResponseDTO atualizar(Integer id, CidadeRequestDTO dados){
        
        Cidade cidade = buscaPorId(id);
        
        if (cidadeRepository.existsByNomeAndUFAndIdCidadeNot(
        dados.getNome(),
        dados.getUF(),
        id)) {

            throw new RuntimeException("Cidade já cadastrada.");
        }

        cidade.setNome(dados.getNome());
        cidade.setUF(dados.getUF());
        
        cidade = cidadeRepository.save(cidade);

        return CidadeResponseDTO.from(cidade);
    }
}

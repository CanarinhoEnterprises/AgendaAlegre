package br.ufes.reserva_espacos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.modelotermodto.CadastroModeloTermoDTO;
import br.ufes.reserva_espacos.entity.ModeloTermo;
import br.ufes.reserva_espacos.repositories.ModeloTermoRepository;
import jakarta.transaction.Transactional;

@Service
public class ModeloTermoService {

	private final ModeloTermoRepository modeloTermoRepository;

	public ModeloTermoService(ModeloTermoRepository modeloTermoRepository) {
		this.modeloTermoRepository = modeloTermoRepository;
	}

	@Transactional
	public ModeloTermo cadastrar(CadastroModeloTermoDTO dto) {
		ModeloTermo modeloTermo = new ModeloTermo();
		modeloTermo.setVersao(dto.getVersao());
		modeloTermo.setTitulo(dto.getTitulo());
		modeloTermo.setTexto(dto.getTexto());
		modeloTermo.setDescricao(dto.getDescricao());
		modeloTermo.setAtivo(dto.getAtivo());
		modeloTermo.setDtCriacao(LocalDate.now());
		return modeloTermoRepository.save(modeloTermo);
	}

	public List<ModeloTermo> listar() {
		return modeloTermoRepository.findAllByOrderByVersaoDesc();
	}

	public ModeloTermo buscarPorId(Integer id) {
		return modeloTermoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Modelo de termo não encontrado."));
	}

	@Transactional
	public void excluir(Integer id) {
		if (!modeloTermoRepository.existsById(id)) {
			throw new RuntimeException("Modelo de termo não encontrado.");
		}

		modeloTermoRepository.deleteById(id);
	}

	@Transactional
	public ModeloTermo atualizar(Integer id, CadastroModeloTermoDTO dto) {
		ModeloTermo modeloTermo = buscarPorId(id);
		modeloTermo.setVersao(dto.getVersao());
		modeloTermo.setTitulo(dto.getTitulo());
		modeloTermo.setTexto(dto.getTexto());
		modeloTermo.setDescricao(dto.getDescricao());
		modeloTermo.setAtivo(dto.getAtivo());
		return modeloTermoRepository.save(modeloTermo);
	}
}

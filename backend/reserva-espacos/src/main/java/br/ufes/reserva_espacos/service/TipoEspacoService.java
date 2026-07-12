package br.ufes.reserva_espacos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.tipoespacodto.CadastroTipoEspacoDTO;
import br.ufes.reserva_espacos.entity.TipoEspaco;
import br.ufes.reserva_espacos.repositories.TipoEspacoRepository;
import jakarta.transaction.Transactional;

@Service
public class TipoEspacoService {

	private final TipoEspacoRepository tipoEspacoRepository;

	public TipoEspacoService(TipoEspacoRepository tipoEspacoRepository) {
		this.tipoEspacoRepository = tipoEspacoRepository;
	}

	@Transactional
	public TipoEspaco cadastrar(CadastroTipoEspacoDTO dto) {
		TipoEspaco tipoEspaco = new TipoEspaco();
		tipoEspaco.setNome(dto.getNome());
		tipoEspaco.setDescricao(dto.getDescricao());
		return tipoEspacoRepository.save(tipoEspaco);
	}

	public List<TipoEspaco> listar() {
		return tipoEspacoRepository.findAllByOrderByNomeAsc();
	}

	public TipoEspaco buscarPorId(Integer id) {
		return tipoEspacoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Tipo de espaço não encontrado."));
	}

	@Transactional
	public void excluir(Integer id) {
		if (!tipoEspacoRepository.existsById(id)) {
			throw new RuntimeException("Tipo de espaço não encontrado.");
		}

		tipoEspacoRepository.deleteById(id);
	}

	@Transactional
	public TipoEspaco atualizar(Integer id, CadastroTipoEspacoDTO dto) {
		TipoEspaco tipoEspaco = buscarPorId(id);
		tipoEspaco.setNome(dto.getNome());
		tipoEspaco.setDescricao(dto.getDescricao());
		return tipoEspacoRepository.save(tipoEspaco);
	}
}

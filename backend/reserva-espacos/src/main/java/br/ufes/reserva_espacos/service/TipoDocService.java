package br.ufes.reserva_espacos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.tipodocdto.CadastroTipoDocDTO;
import br.ufes.reserva_espacos.entity.TipoDoc;
import br.ufes.reserva_espacos.repositories.TipoDocRepository;
import jakarta.transaction.Transactional;

@Service
public class TipoDocService {

	private final TipoDocRepository tipoDocRepository;

	public TipoDocService(TipoDocRepository tipoDocRepository) {
		this.tipoDocRepository = tipoDocRepository;
	}

	@Transactional
	public TipoDoc cadastrar(CadastroTipoDocDTO dto) {
		TipoDoc tipoDoc = new TipoDoc();
		tipoDoc.setNome(dto.getNome());
		tipoDoc.setDescricao(dto.getDescricao());
		return tipoDocRepository.save(tipoDoc);
	}

	public List<TipoDoc> listar() {
		return tipoDocRepository.findAllByOrderByNomeAsc();
	}

	public TipoDoc buscarPorId(Integer id) {
		return tipoDocRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Tipo de documento não encontrado."));
	}

	@Transactional
	public void excluir(Integer id) {
		if (!tipoDocRepository.existsById(id)) {
			throw new RuntimeException("Tipo de documento não encontrado.");
		}

		tipoDocRepository.deleteById(id);
	}

	@Transactional
	public TipoDoc atualizar(Integer id, CadastroTipoDocDTO dto) {
		TipoDoc tipoDoc = buscarPorId(id);
		tipoDoc.setNome(dto.getNome());
		tipoDoc.setDescricao(dto.getDescricao());
		return tipoDocRepository.save(tipoDoc);
	}
}

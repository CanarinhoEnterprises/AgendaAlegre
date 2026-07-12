package br.ufes.reserva_espacos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.ufes.reserva_espacos.dto.espacodto.CadastroEspacoDTO;
import br.ufes.reserva_espacos.entity.Endereco;
import br.ufes.reserva_espacos.entity.Espaco;
import br.ufes.reserva_espacos.entity.RecursoAcessibilidade;
import br.ufes.reserva_espacos.entity.TipoEspaco;
import br.ufes.reserva_espacos.repositories.EnderecoRepository;
import br.ufes.reserva_espacos.repositories.EspacoRepository;
import br.ufes.reserva_espacos.repositories.RecursoAcessibilidadeRepository;
import br.ufes.reserva_espacos.repositories.TipoEspacoRepository;
import jakarta.transaction.Transactional;

@Service
public class EspacoService {

	private final EspacoRepository espacoRepository;
	private final EnderecoRepository enderecoRepository;
	private final TipoEspacoRepository tipoEspacoRepository;
	private final RecursoAcessibilidadeRepository recursoAcessibilidadeRepository;

	public EspacoService(EspacoRepository espacoRepository,
			EnderecoRepository enderecoRepository,
			TipoEspacoRepository tipoEspacoRepository,
			RecursoAcessibilidadeRepository recursoAcessibilidadeRepository) {
		this.espacoRepository = espacoRepository;
		this.enderecoRepository = enderecoRepository;
		this.tipoEspacoRepository = tipoEspacoRepository;
		this.recursoAcessibilidadeRepository = recursoAcessibilidadeRepository;
	}

	@Transactional
	public Espaco cadastrar(CadastroEspacoDTO dto) {
		Espaco espaco = new Espaco();
		preencherEspaco(espaco, dto);
		return espacoRepository.save(espaco);
	}

	public List<Espaco> listar() {
		return espacoRepository.findAllByOrderByNomeAsc();
	}

	public Espaco buscarPorId(Integer id) {
		return espacoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Espaço não encontrado."));
	}

	@Transactional
	public void excluir(Integer id) {
		if (!espacoRepository.existsById(id)) {
			throw new RuntimeException("Espaço não encontrado.");
		}

		espacoRepository.deleteById(id);
	}

	@Transactional
	public Espaco atualizar(Integer id, CadastroEspacoDTO dto) {
		Espaco espaco = buscarPorId(id);
		preencherEspaco(espaco, dto);
		return espacoRepository.save(espaco);
	}

	private void preencherEspaco(Espaco espaco, CadastroEspacoDTO dto) {
		if (dto.getCapacidade() == null || dto.getCapacidade() <= 0) {
			throw new RuntimeException("Capacidade deve ser maior que zero.");
		}

		Endereco endereco = enderecoRepository.findById(dto.getIdEndereco())
				.orElseThrow(() -> new RuntimeException("Endereço não encontrado."));

		TipoEspaco tipoEspaco = tipoEspacoRepository.findById(dto.getIdTipoEspaco())
				.orElseThrow(() -> new RuntimeException("Tipo de espaço não encontrado."));

		espaco.setEndereco(endereco);
		espaco.setTipoEspaco(tipoEspaco);
		espaco.setCapacidade(dto.getCapacidade());
		espaco.setNome(dto.getNome());
		espaco.setDescricao(dto.getDescricao());
		espaco.setUrlCapa(dto.getUrlCapa());
		espaco.setStatus(dto.getStatus());
		espaco.setRecursosAcessibilidade(carregarRecursos(dto.getIdsRecursosAcessibilidade()));
	}

	private List<RecursoAcessibilidade> carregarRecursos(List<Integer> idsRecursos) {
		if (idsRecursos == null || idsRecursos.isEmpty()) {
			return new ArrayList<>();
		}

		List<RecursoAcessibilidade> recursos = recursoAcessibilidadeRepository.findAllById(idsRecursos);
		if (recursos.size() != idsRecursos.size()) {
			throw new RuntimeException("Um ou mais recursos de acessibilidade não foram encontrados.");
		}

		return recursos;
	}
}

package br.ufes.reserva_espacos.dto.arquivodto;

public class CadastroArquivoDTO {
    private Integer idTipoDoc;
    private Integer idPendencia;
    private String nome;
    private String url;
    private String extensao;
    private Long tamanho;

    public CadastroArquivoDTO() {
    }

    public CadastroArquivoDTO(Integer idTipoDoc, Integer idPendencia, String nome, String url, String extensao, Long tamanho) {
        this.idTipoDoc = idTipoDoc;
        this.idPendencia = idPendencia;
        this.nome = nome;
        this.url = url;
        this.extensao = extensao;
        this.tamanho = tamanho;
    }

    public Integer getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(Integer idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public Integer getIdPendencia() {
        return idPendencia;
    }

    public void setIdPendencia(Integer idPendencia) {
        this.idPendencia = idPendencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }
}

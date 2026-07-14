package br.ufes.reserva_espacos.dto.analisereservadto;

    public class CancelarReservaResponseDTO {

    private Integer idReserva;
    private String mensagem;

    public CancelarReservaResponseDTO(Integer idReserva, String mensagem) {
        this.idReserva = idReserva;
        this.mensagem = mensagem;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public String getMensagem() {
        return mensagem;
    }
}


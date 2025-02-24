package com.yan.desafiobackendspringbatch.Dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public record OutputDTO(Long id
, Integer tipo
, Date data
, BigDecimal valor
, Long cpf
, String cartao
, Time hora
, String donoDaLoja
, String nomeDaLoja) {

    /// Whiter patterns para tratar a diferença dos valores entre input e output

    public OutputDTO withData(String data) throws ParseException {
        var dateFormat = new SimpleDateFormat("yyyyMMdd");
        var date = dateFormat.parse(data);

        return new OutputDTO(this.id,this.tipo,new Date(date.getTime()),this.valor,this.cpf,this.cartao,this.hora,this.donoDaLoja,this.nomeDaLoja);
    }

    public OutputDTO withHora(String hora) throws ParseException {
        var dateFormat = new SimpleDateFormat("HHmmss");
        var hour = dateFormat.parse(hora);

        return new OutputDTO(this.id,this.tipo,this.data,this.valor,this.cpf,this.cartao,new Time(hour.getTime()),this.donoDaLoja,this.nomeDaLoja);
    }

    public OutputDTO withValorDividedByHundred(BigDecimal valor) {
        return new OutputDTO(this.id,this.tipo,this.data,valor.divide(BigDecimal.valueOf(100.00)),this.cpf,this.cartao,this.hora,this.donoDaLoja,this.nomeDaLoja);
    }
}

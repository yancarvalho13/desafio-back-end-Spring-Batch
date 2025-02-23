package com.yan.desafiobackendspringbatch.Dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;

public record OutputDTO(Long id
, Integer tipo
, Date data
, BigDecimal valor
, Long cpf
, String cartao
, Time hora
, String donoDaLoja
, String nomeDaLoja) {
}

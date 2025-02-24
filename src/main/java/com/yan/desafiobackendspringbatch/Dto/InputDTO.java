package com.yan.desafiobackendspringbatch.Dto;

import java.math.BigDecimal;


public record InputDTO(Integer tipo
,String data
,BigDecimal valor
,Long cpf
,String cartao
,String hora
,String donoDaLoja
,String nomeDaLoja) {
}

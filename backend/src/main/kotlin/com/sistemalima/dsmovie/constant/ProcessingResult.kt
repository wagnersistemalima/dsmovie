package com.sistemalima.dsmovie.constant

enum class ProcessingResult(val message: String) {
    START_PROCESS("Inicio do processo da request"),
    GET_MOVIMENT_NUMBER("Movimentação do processo request"),
    END_PROCESS("Fim do processo request"),
    ENTITY_NOT_FOUND_EXCEPTION("Error: Entidade não encontrada"),
    SCORE_EXCEPTION("Error: Não foi possivel salvar sua avaliação")
}

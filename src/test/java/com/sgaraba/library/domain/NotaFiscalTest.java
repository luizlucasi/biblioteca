package com.sgaraba.library.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sgaraba.library.web.rest.TestUtil;

public class NotaFiscalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotaFiscal.class);
        NotaFiscal notaFiscal1 = new NotaFiscal();
        notaFiscal1.setId(1L);
        NotaFiscal notaFiscal2 = new NotaFiscal();
        notaFiscal2.setId(notaFiscal1.getId());
        assertThat(notaFiscal1).isEqualTo(notaFiscal2);
        notaFiscal2.setId(2L);
        assertThat(notaFiscal1).isNotEqualTo(notaFiscal2);
        notaFiscal1.setId(null);
        assertThat(notaFiscal1).isNotEqualTo(notaFiscal2);
    }
}

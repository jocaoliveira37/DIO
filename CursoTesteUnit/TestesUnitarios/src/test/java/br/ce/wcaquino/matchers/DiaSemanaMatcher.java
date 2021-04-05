package br.ce.wcaquino.matchers;


import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Calendar;
import java.util.Date;

public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {

    private Integer diaSemana;
    public DiaSemanaMatcher(Integer diaSemana){
        this.diaSemana = diaSemana;
    }

    public void describeTo(Description desc) {
        Calendar data = Calendar.getInstance();
        data.set(Calendar.DAY_OF_WEEK, diaSemana);
        Date dataExtenso = data.getTime();
        desc.appendText(String.valueOf(dataExtenso));
    }


    protected boolean matchesSafely(Date data) {
        return DataUtils.verificarDiaSemana(data, diaSemana);
    }

}

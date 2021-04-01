package matchers;

import java.util.Calendar;

public class MatchersProperty {
    public static DiaSemanaMatcher caiEm(Integer diaSemana){
        return new DiaSemanaMatcher(diaSemana);
    }
    public static DiaSemanaMatcher caiNumaSegunda(){
        return new DiaSemanaMatcher((Calendar.MONDAY));
    }
}

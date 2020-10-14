package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtil {
	
	public static String getDataFormatada(LocalDate data){
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatador);
    }
	
	public static String calcularDiaMaxDevolucao(int numDiaria){
		LocalDate data = LocalDate.now();
        LocalDate novaData = data.plusDays(numDiaria);
        
        return getDataFormatada(novaData);
	}
}

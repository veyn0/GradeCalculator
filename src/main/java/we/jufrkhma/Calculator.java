package we.jufrkhma;

import we.jufrkhma.data.grades.Grade;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    public List<Grade> getVerbalGrades(List<Grade> grades){
        List<Grade> result = new ArrayList<>();
        for(Grade g : grades){
            if(g.isVerbal()) result.add(g);
        }
        return result;
    }

    public List<Grade> getNonverbalGrades(List<Grade> grades){
        List<Grade> result = new ArrayList<>();
        for(Grade g : grades){
            if(!g.isVerbal()) result.add(g);
        }
        return result;
    }

    private double getAverage(List<Grade> grades){
        int sum = 0;
        for (Grade g : grades){
            sum += g.grade();
        }
        return  (double) sum / grades.size();
    }

    public int getAdjustedGrade(List<Grade> verbalGrades, List<Grade> nonVerbalGrades, float verbalGradeWeight){
        double verbalAvg = getAverage(verbalGrades) * verbalGradeWeight;
        double nonVerbalAvg = getAverage(nonVerbalGrades) * (1-verbalGradeWeight);
        return (int) Math.round(nonVerbalAvg + verbalAvg);
    }
}

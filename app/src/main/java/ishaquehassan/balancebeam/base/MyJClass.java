package ishaquehassan.balancebeam.base;

import com.google.gson.reflect.TypeToken;
import ishaquehassan.balancebeam.models.DietPlanModel;

import java.lang.reflect.Type;
import java.util.Map;

public class MyJClass {
    static Type getMapType(){
        return new TypeToken<Map<String,DietPlanModel[][]>>(){}.getType();
    }
}

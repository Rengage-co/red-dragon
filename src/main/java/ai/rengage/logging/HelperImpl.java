package ai.rengage.logging;

public class HelperImpl implements Helpers{

    @Override
    public boolean test2(String result) {

        if (result.length()==0){
            return true;
        }
        return false;
    }
}

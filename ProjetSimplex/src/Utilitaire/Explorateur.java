package Utilitaire;

import java.lang.reflect.Field;

public abstract class Explorateur
{
    public static Object getField(Object obj, String s) 
    {
        Object obj1 = null;
        Class class1 = obj.getClass();

        try
        {
            Field field;
            (field = class1.getDeclaredField(s)).setAccessible(true);
            obj1 = field.get(obj);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return obj1;
    }
}

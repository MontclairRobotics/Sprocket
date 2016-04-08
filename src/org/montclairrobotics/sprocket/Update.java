import java.util.ArrayList
public class Update
{
//i did this on a phone
private static ArrayList<Updateable> objects=new ArrayList<Updateable>()

public static void add(Updateable obj)
{
objects.add(obj);
}

public static void update()
{
for(Updateable obj:objects)
{
obj.update();
}
}
}

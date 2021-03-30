public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T>
{
    public ArrayUnorderedList()
    {
        super();
    }

    public ArrayUnorderedList(int initialCapacity)
    {
        super(initialCapacity);
    }

    public void addToFront(T element)
    {
        if(size() == list.length)
        {
            expandCapacity();
        }

        for(int i = this.size();i > 0;i--)
        {
            this.list[i] = this.list[i-1];
        }

        this.list[0] = element;
        this.rear++;
    }

    public void addToRear(T element)
    {
        if(size() == list.length)
        {
            expandCapacity();
        }

        this.list[rear] = element;
        this.rear++;
    }

    public void addAfter(T element, T target)
    {
        if(size() == list.length)
        {
            expandCapacity();
        }

        int scan = 0;
        while(scan < rear && !target.equals(list[scan]))
        {
            scan++;
        }

        if(scan == rear)
        {
            throw new ElementNotFoundException("list");
        }

        scan++;
        for(int scan2 = rear; scan2 > scan; scan2--)
        {
            list[scan2] = list[scan2 - 1];
        }

        list[scan] = element;
        rear++;
    }
}

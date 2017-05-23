package clonefix.objects.factory;

import clonefix.objects.Clone;

import java.util.ArrayList;
import java.util.List;

public class ClonesGroup {

    private List<Clone> cloneList = new ArrayList<Clone>();

    public ClonesGroup() {
        // Nothing to do
    }


    public void addClone(Clone clone) {
        cloneList.add(clone);
    }

    public void addAllClone(List<Clone> cloneList) {
        this.cloneList = cloneList;
    }

    public List<Clone> getCloneList() {
        return cloneList;
    }
}

/**
 * 子条目(具体信息)
 */

public class ChapterItem {
    private int id;
    private String name;
    private int pid;

    public ChapterItem() {

    }

    public ChapterItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}

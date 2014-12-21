package hunting.common.maptree;

import java.util.ArrayList;
import java.util.List;

public class HuntingDtree {

    private Node root;
    
    public static class NodeData {
        private String playerId;
        private String gameCtiy;
        private long gameInfoId;
        
        public NodeData (String playerId, String gameCtiy, Long gameInfoId) {
            this.playerId = playerId;
            this.gameCtiy = gameCtiy;
            this.gameInfoId = gameInfoId;
        }

        
        public String getPlayerId() {
            return playerId;
        }

        
        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        
        public String getGameCtiy() {
            return gameCtiy;
        }

        
        public void setGameCtiy(String gameCtiy) {
            this.gameCtiy = gameCtiy;
        }

        
        public long getGameInfoId() {
            return gameInfoId;
        }

        
        public void setGameInfoId(long gameInfoId) {
            this.gameInfoId = gameInfoId;
        }

    }

    public static class Node {

        public double x, y;              // x- and y- coordinates
        Node NW, NE, SE, SW;            // four subtrees
        public NodeData s;                // associated data

        Node(double x, double y, NodeData s) {
            this.x = x;
            this.y = y;
            this.s = s;
      }

        @Override
        public String toString() {
            return s + "( " + x + "," + y + " )";
        }
    }

    public static class Box {

        double xlow, ylow, xhigh, yhigh;

        public Box(double xlow, double ylow, double xhigh, double yhigh) {
            this.xlow = xlow;
            this.xhigh = xhigh;
            this.ylow = ylow;
            this.yhigh = yhigh;
        }

        public boolean contains(double x, double y) {
            return (x >= xlow && y >= ylow && x <= xhigh && y <= yhigh);
        }

        @Override
        public String toString() {
            return "( " + xlow + ", " + ylow + ", " + xhigh + ", " + yhigh + " )";
        }
    }


    public void insert(double x, double y, NodeData s) {
        root = insert(root, x, y, s);
    }

    private Node insert(Node h, double x, double y, NodeData s) {
        if (h == null)
            return new Node(x, y, s);

        else if (x < h.x && y < h.y)
            h.SW = insert(h.SW, x, y, s);
        else if (x < h.x && !(y < h.y))
            h.NW = insert(h.NW, x, y, s);
        else if (!(x < h.x) && y < h.y)
            h.SE = insert(h.SE, x, y, s);
        else if (!(x < h.x) && !(y < h.y))
            h.NE = insert(h.NE, x, y, s);
        return h;
    }

    public List<Node> query(Box box) {
        List<Node> result = new ArrayList<Node>();
        query(root, box, result);
        return result;
    }

    private void query(Node h, Box box, List<Node> result) {
        if (h == null)
            return;

        if (box.contains(h.x, h.y))
            result.add(h);

        if ( (box.xlow < h.x) &&  (box.ylow < h.y))
            query(h.SW, box, result);
        if ( (box.xlow < h.x) && !(box.yhigh < h.y))
            query(h.NW, box, result);
        if (!(box.xhigh < h.x) &&  (box.ylow < h.y))
            query(h.SE, box, result);
        if (!(box.xhigh < h.x) && !(box.yhigh < h.y))
            query(h.NE, box, result);
    }

}

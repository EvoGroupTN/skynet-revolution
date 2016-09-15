import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    private int linksNumber;
    private int[] linkFirstNodes;
    private int[] linkSecondNodes;
    private int exitGatewaysNumber;
    private int[] gatewaysNodes;

    public Player(int linksNumber, int[] linkFirstNodes,
                  int[] linkSecondNodes, int exitGatewaysNumber, int[] gatewaysNodes)
    {
        this.linksNumber = linksNumber;
        this.linkFirstNodes = linkFirstNodes;
        this.linkSecondNodes = linkSecondNodes;
        this.exitGatewaysNumber = exitGatewaysNumber;
        this.gatewaysNodes = gatewaysNodes;
    }


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int nodesNumber = in.nextInt();
        int linksNumber = in.nextInt();
        int exitGatewaysNumber = in.nextInt();
        int[] linkFirstNodes = new int[linksNumber];
        int[] linkSecondNodes = new int[linksNumber];
        int[] gatewaysNodes = new int[exitGatewaysNumber];
        for (int linkCounter = 0; linkCounter < linksNumber; linkCounter++) {
            linkFirstNodes[linkCounter] = in.nextInt();
            linkSecondNodes[linkCounter] = in.nextInt();
        }
        for (int exitGatewayCounter = 0; exitGatewayCounter < exitGatewaysNumber; exitGatewayCounter++) {
            gatewaysNodes[exitGatewayCounter] = in.nextInt();
        }

        Player player = new Player(linksNumber, linkFirstNodes,
                linkSecondNodes, exitGatewaysNumber, gatewaysNodes);

        // player loop
        while (true) {
            int agentCurrentPosition = in.nextInt();
            player.play(agentCurrentPosition);
        }
    }

    /**
     * A method to print the needed action
     **/
    public void play(int agentCurrentPosition)
    {
        int[] nodes = getNeighbourNodes(agentCurrentPosition);
        for (int nodeCount = 0; nodeCount<nodes.length;nodeCount++) {
            if (isGateway(nodes[nodeCount])) {
                removeLink(agentCurrentPosition, nodes[nodeCount]);
                return;
            }
        }
        // if there is no gateway among the neighbour nodes => remove the first link.
        removeLink(agentCurrentPosition, nodes[0]);
        return;
    }

    private void removeLink(int firstNode,int secondNode) {
        System.out.println(firstNode + " " + secondNode);
        int linkPosition = -1;
        for (int linkIndex = 0;linkIndex < linksNumber; linkIndex++) {
            if (linkFound(firstNode, secondNode, linkIndex))
            {
                linkPosition = linkIndex;
            }
        }
        for (int linkIndex = linkPosition; linkIndex<linksNumber-1; linkIndex++) {
            linkFirstNodes[linkIndex] = linkFirstNodes[linkIndex+1];
            linkSecondNodes[linkIndex] = linkSecondNodes[linkIndex+1];
        }
        linksNumber--;
        linkFirstNodes = Arrays.copyOf(linkFirstNodes, linksNumber);
        linkSecondNodes = Arrays.copyOf(linkSecondNodes, linksNumber);
    }

    private boolean linkFound(int firstNode, int secondNode, int linkIndex) {
        return (linkFirstNodes[linkIndex] == firstNode && linkSecondNodes[linkIndex] == secondNode) ||
                (linkFirstNodes[linkIndex] == secondNode && linkSecondNodes[linkIndex] == firstNode);
    }

    private int[] getNeighbourNodes(int currentPosition)
    {
        int[] neighbourNodes = new int[linksNumber];
        int neighbourNodesIndex = 0;
        for (int linkIndex = 0; linkIndex < linksNumber; linkIndex++) {
            if (currentPosition == linkFirstNodes[linkIndex]) {
                neighbourNodes[neighbourNodesIndex++] = linkSecondNodes[linkIndex];
            }
            if (currentPosition == linkSecondNodes[linkIndex]) {
                neighbourNodes[neighbourNodesIndex++] = linkFirstNodes[linkIndex];
            }
        }
        return Arrays.copyOf(neighbourNodes, neighbourNodesIndex);
    }

    private boolean isGateway(int node) {
        for (int exitGatewayIndex = 0; exitGatewayIndex < exitGatewaysNumber; exitGatewayIndex++) {
            if (node == gatewaysNodes[exitGatewayIndex]) {
                return true;
            }
        }
        return false;
    }
}
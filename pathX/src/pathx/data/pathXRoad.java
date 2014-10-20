/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.data;
import pathx.data.pathXIntersection;
/**
 *
 * @author Joseph
 */


/**
 * This class represents a road in level graph, which means it's 
 * basically a graph edge.
 * 
 * @author Richard McKenna
 */
public class pathXRoad
{
    // THESE ARE THE EDGE'S NODES
    pathXIntersection node1;
    pathXIntersection node2;
    
    // false IF IT'S TWO-WAY, true IF IT'S ONE WAY
    boolean oneWay;
    
    // ROAD SPEED LIMIT
    int speedLimit;

    // ACCESSOR METHODS
    public pathXIntersection getNode1()  {   return node1;       }
    public pathXIntersection getNode2()  {   return node2;       }
    public boolean isOneWay()       {   return oneWay;      }
    public int getSpeedLimit()      {   return speedLimit;  }
    
    // MUTATOR METHODS
    public void setNode1(pathXIntersection node1)    {   this.node1 = node1;             }
    public void setNode2(pathXIntersection node2)    {   this.node2 = node2;             }
    public void setOneWay(boolean oneWay)       {   this.oneWay = oneWay;           }
    public void setSpeedLimit(int speedLimit)   {   this.speedLimit = speedLimit;   }

    /**
     * Builds and returns a textual representation of this road.
     */
    @Override
    public String toString()
    {
        return node1 + " - " + node2 + "(" + speedLimit + ":" + oneWay + ")";
    }
}

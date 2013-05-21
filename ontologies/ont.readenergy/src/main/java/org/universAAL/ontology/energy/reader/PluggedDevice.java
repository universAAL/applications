package org.universAAL.ontology.energy.reader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class PluggedDevice {
    /**
     * Minutes in a week
     */
    private static int STACK_SIZE = 10;
    private String name;
    private Integer maxPwr;
    private Integer minPwr;
    private Integer onTh;
    private Integer stdbyTh;
    private Stack<Integer> measStack;

    public PluggedDevice(String name) {
	this.name = name;
	maxPwr = 0;
	minPwr = 5000;
	onTh = 0;
	stdbyTh = 0;
	measStack = new Stack<Integer>();
	measStack.setSize(STACK_SIZE);
    }

    public PluggedDevice(String name, Integer max, Integer min) {
	this.name = name;
	maxPwr = max;
	minPwr = min;
	onTh = 0;
	stdbyTh = 0;
	measStack = new Stack<Integer>();
	measStack.setSize(STACK_SIZE);
	ArrayList<String> al;
	// al.
	LinkedList ll;

    }

    public void addValue(Integer val) {
	measStack.add(0, val);
	measStack.setSize(STACK_SIZE);
    }

    public void printMeasStack() {
	System.out.print("Printing energy values stack:");
	for (Integer integer : measStack) {
	    System.out.print(integer + "-");

	    java.util.ArrayList<String> a;
	    Integer ab = 2;
	    Integer c = (int) (ab * 0.2);

	}
    }

    public String toString() {
	StringBuffer st = new StringBuffer();
	st.append("Plugged device " + name + " maxPwr " + maxPwr + " minPwr "
		+ minPwr + " onTh " + onTh + " stdbyTh " + stdbyTh);
	return st.toString();
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the maxPwr
     */
    public Integer getMaxPwr() {
	return maxPwr;
    }

    /**
     * @param maxPwr
     *            the maxPwr to set
     */
    public void setMaxPwr(Integer maxPwr) {
	this.maxPwr = maxPwr;
	recalculateThresholds();
    }

    private void recalculateThresholds() {
	int range = maxPwr - minPwr;
	onTh = (Integer.valueOf((int) (range * 0.8)));
	stdbyTh = onTh = (Integer.valueOf((int) (range * 0.2)));
    }

    /**
     * @return the minPwr
     */
    public Integer getMinPwr() {
	return minPwr;
    }

    /**
     * @param minPwr
     *            the minPwr to set
     */
    public void setMinPwr(Integer minPwr) {
	this.minPwr = minPwr;
    }

    /**
     * @return the onTh
     */
    public Integer getOnTh() {
	return onTh;
    }

    /**
     * @param onTh
     *            the onTh to set
     */
    public void setOnTh(Integer onTh) {
	this.onTh = onTh;
    }

    /**
     * @return the stdbyTh
     */
    public Integer getStdbyTh() {
	return stdbyTh;
    }

    /**
     * @param stdbyTh
     *            the stdbyTh to set
     */
    public void setStdbyTh(Integer stdbyTh) {
	this.stdbyTh = stdbyTh;
    }

    /**
     * @return the measStack
     */
    public Stack<Integer> getMeasStack() {
	return measStack;
    }

    /**
     * @param measStack
     *            the measStack to set
     */
    public void setMeasStack(Stack<Integer> measStack) {
	this.measStack = measStack;
    }

}

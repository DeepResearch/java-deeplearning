package org.deeplearning4j.util;

import java.lang.reflect.Array;
import java.util.List;

public class ArrayUtil {




    public static int prod(int[] mult) {
        int ret = 1;
        for(int i = 0; i < mult.length; i++)
            ret *= mult[i];
        return ret;
    }


    public static int[] consArray(int a, int[] as) {
        int len=as.length;
        int[] nas=new int[len+1];
        nas[0]=a;
        System.arraycopy(as, 0, nas, 1, len);
        return nas;
    }

    public static int[] removeIndex(int[] data, int index) {
        int len=data.length;
        int[] result=new int[len-1];
        System.arraycopy(data, 0, result, 0, index);
        System.arraycopy(data, index+1, result, index, len-index-1);
        return result;
    }

    /**
     * Computes the standard packed array strides for a given shape.
     * @param shape the shape of a matrix:
     * @return the strides for a matrix of n dimensions
     */
    public static  int[] calcStrides(int[] shape) {
        int dimensions = shape.length;
        int[] stride=new int[dimensions];
        int st=1;
        for (int j= dimensions - 1; j >= 0; j--) {
            stride[j] = st;
            st *= shape[j];
        }

        return stride;
    }



    public static  int[] reverseCopy(int[] e) {
        int[] copy = new int[e.length];
        for(int i = 0; i <= e.length / 2; i++)
        {
            int temp = e[i];
            copy[i] = e[e.length - i - 1];
            copy[e.length - i - 1] = temp;
        }
        return copy;
    }


    public static  void reverse(int[] e) {
        for(int i = 0; i <= e.length / 2; i++)
        {
            int temp = e[i];
            e[i] = e[e.length - i - 1];
            e[e.length - i - 1] = temp;
        }

    }

    public static <E> void reverse(E[] e) {
        for(int i = 0; i < e.length / 2; i++)
        {
            E temp = e[i];
            e[i] = e[e.length - i - 1];
            e[e.length - i - 1] = temp;
        }

    }


	public static int[] flatten(int[][] arr) {
		int[] ret = new int[arr.length * arr[0].length];
		int count = 0;
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				ret[count++] = arr[i][j];
		return ret;
	}
	
	public static double[] flatten(double[][] arr) {
		double[] ret = new double[arr.length * arr[0].length];
		int count = 0;
		for(int i = 0; i < arr.length; i++)
			for(int j = 0; j < arr[i].length; j++)
				ret[count++] = arr[i][j];
		return ret;
	}
	
	public static double[][] toDouble(int[][] arr) {
		double[][] ret = new double[arr.length][arr[0].length];
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++)
				ret[i][j] = arr[i][j];
		}
		return ret;
	}


    /**
     * Combines a set of int arrays in to one flat int array
     * @param nums the int arrays to combine
     * @return one combined int array
     */
    public static float[] combineFloat(List<float[]> nums) {
        int length = 0;
        for(int i = 0; i < nums.size(); i++)
            length += nums.get(i).length;
        float[] ret = new float[length];
        int count = 0;
        for(float[] i : nums) {
            for(int j = 0; j < i.length; j++) {
                ret[count++] = i[j];
            }
        }

        return ret;
    }


    /**
     * Combines a set of int arrays in to one flat int array
     * @param nums the int arrays to combine
     * @return one combined int array
     */
    public static double[] combine(List<double[]> nums) {
        int length = 0;
        for(int i = 0; i < nums.size(); i++)
            length += nums.get(i).length;
        double[] ret = new double[length];
        int count = 0;
        for(double[] i : nums) {
            for(int j = 0; j < i.length; j++) {
                ret[count++] = i[j];
            }
        }

        return ret;
    }

    /**
     * Combines a set of int arrays in to one flat int array
     * @param ints the int arrays to combine
     * @return one combined int array
     */
    public static double[] combine(double[]...ints) {
        int length = 0;
        for(int i = 0; i < ints.length; i++)
            length += ints[i].length;
        double[] ret = new double[length];
        int count = 0;
        for(double[] i : ints) {
            for(int j = 0; j < i.length; j++) {
                ret[count++] = i[j];
            }
        }

        return ret;
    }

    /**
     * Combines a set of int arrays in to one flat int array
     * @param ints the int arrays to combine
     * @return one combined int array
     */
    public static int[] combine(int[]...ints) {
        int length = 0;
        for(int i = 0; i < ints.length; i++)
            length += ints[i].length;
        int[] ret = new int[length];
        int count = 0;
        for(int[] i : ints) {
            for(int j = 0; j < i.length; j++) {
                ret[count++] = i[j];
            }
        }

        return ret;
    }

    public static <E> E[] combine(E[]...arrs) {
        int length = 0;
        for(int i = 0; i < arrs.length; i++)
            length += arrs[i].length;

        E[] ret =  (E[]) Array.newInstance(arrs[0][0].getClass(), length);
        int count = 0;
        for(E[] i : arrs) {
            for(int j = 0; j < i.length; j++) {
                ret[count++] = i[j];
            }
        }

        return ret;
    }


	public static int[] toOutcomeArray(int outcome,int numOutcomes) {
		int[] nums = new int[numOutcomes];
		nums[outcome] = 1;
		return nums;
	}

}

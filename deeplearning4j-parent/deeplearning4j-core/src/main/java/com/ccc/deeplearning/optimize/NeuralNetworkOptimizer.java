package com.ccc.deeplearning.optimize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jblas.DoubleMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.mallet.optimize.LimitedMemoryBFGS;
import cc.mallet.optimize.Optimizable;
import cc.mallet.optimize.Optimizer;

import com.ccc.deeplearning.nn.BaseNeuralNetwork;
import com.ccc.deeplearning.util.MyConjugateGradient;
/**
 * Performs basic beam search based on the network's loss function
 * @author Adam Gibson
 *
 */
public abstract class NeuralNetworkOptimizer implements Optimizable.ByGradientValue,Serializable {

	public NeuralNetworkOptimizer(BaseNeuralNetwork network,double lr,Object[] trainingParams) {
		this.network = network;
		this.lr = lr;
		this.extraParams = trainingParams;
	}


	private static final long serialVersionUID = 4455143696487934647L;
	protected BaseNeuralNetwork network;
	protected double lr;
	protected Object[] extraParams;
	protected double tolerance = 0.00000001;
	protected static Logger log = LoggerFactory.getLogger(NeuralNetworkOptimizer.class);
	protected List<Double> errors = new ArrayList<Double>();
	protected double minLearningRate = 0.001;
	protected transient MyConjugateGradient opt;

	public void train(DoubleMatrix x) {
		if(opt == null)
			opt = new MyConjugateGradient(this);
		opt.setTolerance(tolerance);
		opt.optimize();


	}


	public List<Double> getErrors() {
		return errors;
	}


	@Override
	public int getNumParameters() {
		return network.W.length + network.hBias.length + network.vBias.length;
	}


	@Override
	public void getParameters(double[] buffer) {
		/*
		 * If we think of the parameters of the model (W,vB,hB)
		 * as a solid line for the optimizer, we get the following:
		 * 
		 */
		for(int i = 0; i < buffer.length; i++)
			buffer[i] = getParameter(i);
	}


	@Override
	public double getParameter(int index) {
		//beyond weight matrix
		if(index >= network.W.length) {
			int i = getAdjustedIndex(index);
			//beyond visible bias
			if(index >= network.vBias.length + network.W.length) {
				return network.hBias.get(i);
			}
			else
				return network.vBias.get(i);

		}
		else 
			return network.W.get(index);



	}


	@Override
	public void setParameters(double[] params) {
		/*
		 * If we think of the parameters of the model (W,vB,hB)
		 * as a solid line for the optimizer, we get the following:
		 * 
		 */
		for(int i = 0; i < params.length; i++)
			setParameter(i,params[i]);

	}


	@Override
	public void setParameter(int index, double value) {
		//beyond weight matrix
		if(index >= network.W.length) {
			//beyond visible bias
			if(index >= network.vBias.length + network.W.length)  {
				int i = getAdjustedIndex(index);
				network.hBias.put(i, value);
			}
			else {
				int i = getAdjustedIndex(index);
				network.vBias.put(i,value);

			}

		}
		else {
			network.W.put(index,value);
		}
	}


	private int getAdjustedIndex(int index) {
		int wLength = network.W.length;
		int vBiasLength = network.vBias.length;
		if(index < wLength)
			return index;
		else if(index >= wLength + vBiasLength) {
			int hIndex = index - wLength - vBiasLength;
			return hIndex;
		}
		else {
			int vIndex = index - wLength;
			return vIndex;
		}
	}


	@Override
	public abstract void getValueGradient(double[] buffer);


	@Override
	public double getValue() {
		return -network.getReConstructionCrossEntropy();
	}



}
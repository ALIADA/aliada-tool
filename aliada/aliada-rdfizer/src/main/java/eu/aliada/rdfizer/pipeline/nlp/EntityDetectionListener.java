package eu.aliada.rdfizer.pipeline.nlp;

public interface EntityDetectionListener {

	void onEntityDetected(String tag, String entity);

}

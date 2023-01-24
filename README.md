# demoKafkaBatch

reproduce the problem where throwing a `BatchListenerFailedException` in consumer logic in Batch mode does not interpret
the exception as such and does not do what is expected.
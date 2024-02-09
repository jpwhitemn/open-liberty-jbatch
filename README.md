# Open Liberty/Jakarta Batch Example # 

Author:  Jim White
Copyright:  2024, James White

A simple, and crude example of a Jakarta Batch (JBatch) application running on Open Liberty server.

There are two JBatch batch examples:
1. A Batchlet that just produces a large number of factoral numbers.
2. A standard Batch Job with ItemReader, Writer and Processor classes for "Chunk" processing.  This batch job takes a large data collection (from OpenML - data was gathered from participants in experimental speed dating events) and produces some analysis results.

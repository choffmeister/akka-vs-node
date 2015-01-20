done = function(summary, latency, requests)
  io.stderr:write(string.format("%d\t%d\t%d\t%d\t%d\n",
    summary.requests / summary.duration * 1000 * 1000,
    latency.mean,
    latency:percentile(50),
    latency:percentile(90),
    latency:percentile(99)))
end

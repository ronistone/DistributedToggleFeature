
# Distributed Feature Toggle

## Finder

 - To enable or disable the Finder, use the property
    - feature.toggle.finder.enable
    - With the finder disable, the project loses the "distributed" feature

 - Search for peers using ips configured in the properties:
    - feature.toggle.finder.pairs  
        - used to specify all peer IPs
    - feature.toggle.finder.pairs.subnet  
        - subnet where the finder needs to search (123.123.123.123/12)
    - feature.toggle.finder.pairs.subnet.begin and feature.toggle.finder.pairs.subnet.end
        - initial ip and final ip for peer search on the subnet
 - Peer IP generator strategy, use the property
    - feature.toggle.finder.ip.iteration.strategy
        - values
            - full
                - preprocess all ips on the subnet
            - lazy
                - generates the ip only when requested and after discarding the ip
                

## TODO
    
    - Remove all printStacktrace() and use the logger API
    - Improve the factory class
    - Implements new finders logic (example: MultiThreadFinder - parallel peers seach...)
    - Pass to property all configurable parameters (example: thread pool size...)
    - API to produce metrics to know which features have problems (internal method for developer call when there is an error? API where the developer provides callbacks where the API handles exception and rethrow...)
    - A standalone Feature Toggle server
    - Choose a name to project
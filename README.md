# Actor Commons

This project contains code which Actor team uses in its projects.

If you with to add all common libraries, put the following to your build.sbt:

```
libraryDependencies += "im.actor" %% "actor-commons" % 0.0.6
```

## Concurrent

Contains code which helps to deal with concurrency.

* [ActorFutures](https://github.com/actorapp/commons/blob/master/actor-concurrent/src/main/scala/im/actor/concurrent/ActorFutures.scala) trait helps to handle Future result in Actor's receive loop.
* [FutureExt](https://github.com/actorapp/commons/blob/master/actor-concurrent/src/main/scala/im/actor/concurrent/FutureExt.scala) contains function for to processing sequence of futures in sequential order.

```
libraryDependencies += "im.actor" %% "actor-concurrent" % 0.0.6
```

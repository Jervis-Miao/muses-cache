# 这是一个使用redisson客户端的redis缓存
重要提醒：请重写[CodecFactory#getRedissonCodecInstance()](src/main/java/com/muses/cache/CodecFactory.java#L15-L32)
方法从Spring上下文获取Codec序列化实例，否则系统会存在不可预料的问题。



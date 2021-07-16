main = do
    print . test2 . Just $ 11
    print . test2 . Just $ 110
    print . test2 $ Nothing

test :: (Num a) => a -> a
test a = a + 10

test2 :: (Num a) => Maybe a -> Maybe a
test2 (Nothing ) = Nothing
test2 (Just a) = Just $ test a

test3 :: (Num a) => Maybe a -> a
test3 m = do
    a <- m
    return a



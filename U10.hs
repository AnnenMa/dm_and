--Aufgabe 4
mapFns :: [(a->b)] -> [a] -> [b]
mapFns (fs) [] = []
mapFns (fs) (x:xs) = [(f x)| f<-fs] ++ (mapFns fs xs)

--Aufgabe 5
maybemap :: (a -> b) -> [Maybe a] -> [Maybe b]
maybemap f [Just a] = [Just (f a)]
maybemap f [Nothing] = [Nothing]
maybemap f ((Nothing):xs) = [Nothing] ++ (maybemap f xs)
maybemap f ((Just a):xs) = [Just (f a)] ++ (maybemap f xs)
setwd("/Users/jaivrat/algs4/excercise2")
getwd()

#d <- read.table("order.txt.old1")
d <- read.table("order.txt")
d <- d[d$V2 != 0.0,]
d
N <- d$V1
lgN <- log(N)
lgN
secs <- d$V2
secs
lgsecs <- log(secs)

lm(lgsecs ~ lgN)

x <- 1:30
y <- 2*x
x
y
rnds <- rnorm(30)
rnds
y <- y + rnds
lm(y~x)

make.accumulator<-function(){ 
    a<-0 
    function(x) { 
        a<<-a+x 
        a 
    } 
} 

f<-make.accumulator() 
g<-make.accumulator() 
f(1)
g(1)


x <- LETTERS[seq( from = 1, to = 20 )]
x
?runif
#Moli's algo
rnd <- runif( n = 10, min = 0.5, max = 10)
rnd <- 2*rnd
rnd
res <- round(rnd)
tapply(res,as.factor(res), length)

#Jai Vrat's algo
genSequence <- function(N)
{
    x <- LETTERS[seq( from = 1, to = 26 )]
    rnd <- runif( n = N, min =0, max = 1)
    rnd <- 26 * rnd
    res <- ceiling(rnd)
    x[res]
}

x <- genSequence(20)
y <- genSequence(20)

tapply(res,as.factor(res), length)
res

cacheSeq <- function()
{ 
    mres <- NULL
    get <- function() mres
    set <- function(newres)
    {
        mres <<- newres
    }
    
    add <- function(elem)
    {
        mres <<- c(mres,elem)
    }
    list(set = set, get = get, add=add)
}

lcs <- function(ob, x, y, xi = length(x), yj = length(y))
{
    cres <- ob$get()
    len <- 0
    #validate input
    if(xi > length(x))
        stop("Invalid input x or xi")
    
    if(yj > length(y))
        stop("Invalid input y or yj")
   
    if(xi < 1 || yj < 1){
        len <- 0
    } else if (x[xi] == y[yj]) {
        seqObj <- cacheSeq()
        len <- 1 + lcs(seqObj,x,y,xi-1,yj-1)
        cres <- c(seqObj$get(),x[xi])
    } else {
        seqObj1 <- cacheSeq()
        seqObj2 <- cacheSeq()
        len1 <- lcs(seqObj1,x,y,xi-1,yj)
        len2 <- lcs(seqObj2,x,y,xi,yj-1)
        if(len1 > len2)
        {
            len <- len1
            cres <- c(seqObj1$get(),cres)
            ##cat("len1" + cres)
        }
        else {
            len <- len2
            cres <- c(seqObj2$get(),cres)
            ##cat("len2" + cres)
        }
    }
    ob$set(cres)
    len
}


#in1 <- c("A","B","C","B","D","A","B")
#in2 <- c("B","D","C","A","B","A")
in1 <- c("A","B","C")
in1 <- c("B","C", "A")
seqObj <- cacheSeq()
lcs(seqObj,in1,in2)
seqObj$get()


#

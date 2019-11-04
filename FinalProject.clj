(use 'clojure.java.io)
(require '[clojure.string :as str])

(defn GenInLinksMap [filePath]
	(def outMap {})
	(doseq [x (range 0 10000)]
		(def outMap (assoc outMap x []))
	)
	(with-open [rdr (reader filePath)]
	  (doseq [line (line-seq rdr)]
	  	(doseq [key (rest (str/split line #" "))]
	  		(def outMap (assoc outMap key (conj (get outMap key) (first (str/split line #" ")))))
	  	)
			)
  )
  outMap
)
(def inLinksMap (doall (GenInLinksMap "pages.txt")))

(defn GenOutCountMap [filePath]
	(def outMap {})
	(with-open [rdr (reader filePath)]
		(doseq [line (line-seq rdr)]
			(def outMap (assoc outMap (first (str/split line #" ")) (dec (count (str/split line #" ")))))
		)
	)
	outMap
)
(def outLinksCount (doall (GenOutCountMap "pages.txt")))

(defn InitialRanks []
	(def outMap {})
	(doseq [x (range 0 10000)]
		(def outMap (assoc outMap (str x) (vector x 1)))
	)
	outMap
)
(def ranksMap (doall (InitialRanks)))

(defn UpdateRank [rankVector]
	(def runningTotal 0)
	(doseq [x (get inLinksMap (str (get rankVector 0)))]
		(def numOutLinks (get outLinksCount x))
		(def xRank (get (get ranksMap x) 1))
		(def runningTotal (* (double 85/100) (double(/ xRank numOutLinks))))
	)
	(vector (get rankVector 0) (* (double 85/100) runningTotal))
)

; This function came from Clojure source. I modified it allows explicitly setting the 
; number of threads used. Set below variable to desired number.
(def numThreads 2560)
(defn myPmap
  "Like pmap, except better"
  {:added "1.0"
   :static true}
  ([f coll]
   (let [n numThreads
         rets (map #(future (f %)) coll)
         step (fn step [[x & xs :as vs] fs]
                (lazy-seq
                 (if-let [s (seq fs)]
                   (cons (deref x) (step xs (rest s)))
                   (map deref vs))))]
     (step rets (drop n rets))))
  ([f coll & colls]
   (let [step (fn step [cs]
                (lazy-seq
                 (let [ss (map seq cs)]
                   (when (every? identity ss)
                     (cons (map first ss) (step (map rest ss)))))))]
     (pmap #(apply f %) (step (cons coll colls))))))

(time 
	(dotimes [x 1000]
		(doall
			(myPmap UpdateRank ranksMap)
		)
	)
)

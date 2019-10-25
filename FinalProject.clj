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
(def inLinksMap (GenInLinksMap "pages.txt"))

(defn GenOutCountMap [filePath]
	(def outMap {})
	(with-open [rdr (reader filePath)]
		(doseq [line (line-seq rdr)]
			(def outMap (assoc outMap (first (str/split line #" ")) (dec (count (str/split line #" ")))))
		)
	)
	outMap
)
(def outLinksCount (GenOutCountMap "pages.txt"))

(defn InitialRanks []
	(def outMap {})
	(doseq [x (range 0 10000)]
		(def outMap (assoc outMap (str x) (vector x 1)))
	)
	outMap
)
(def ranksMap InitialRanks)


(defn UpdateRank [rankVector]
	; (print (get inLinksMap (str (get rankVector 0))))
	(def runningTotal 0)
	(doseq [x (get inLinksMap (str (get rankVector 0)))]
		(def numOutLinks (get outLinksCount x))
		(def xRank (get (get (ranksMap) x) 1))

		; (print xRank)
		; (print (type (+ runningTotal (/ xRank numOutLinks))))
		; (print (double(/ xRank numOutLinks)) (str "\n"))
		(def runningTotal (* (double 85/100) (double(/ xRank numOutLinks))))
		; (print runningTotal (str "\n"))
	)
	(vector (get rankVector 0) (* (double 85/100) runningTotal))
)

; (print (get (UpdateRank [4444 1]) 0))
; (print (type (UpdateRank [4444 1])))
; (print (type (first (keys (ranksMap)))))
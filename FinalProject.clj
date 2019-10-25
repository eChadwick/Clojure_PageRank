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

		; (print (get ranksMap "0"))
		(def xRank (get (ranksMap) x))
		(print xRank (str "\n"))
	)
)

(UpdateRank [4444 1])
; (print (type (first (keys (ranksMap)))))
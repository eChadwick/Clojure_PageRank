(use 'clojure.java.io)
(require '[clojure.string :as str])

; (defn PrintInput []
; 	(with-open [rdr (reader "pages.txt")]
; 	  (doseq [line (line-seq rdr)]
; 	    (print(str/split line #" "))
; 			)
;   )
; )

(defn ReadData [filePath]
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

(defn InitialRanks []
	(def outMap {})
	(doseq [x (range 0 10000)]
		(def outMap (assoc outMap x 1))
	)
	outMap
)



(def linksMap (ReadData "pages.txt"))
(def ranksMap InitialRanks)

(print (ranksMap))
; (print(get linksMap "9999"))
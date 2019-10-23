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
	(doseq [x (range 0 1000)]
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

; (PrintInput)
(print(get (ReadData "pages.txt") "6666"))
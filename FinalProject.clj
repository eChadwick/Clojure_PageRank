(use 'clojure.java.io)
(require '[clojure.string :as str])

; (defn PrintInput []
; 	(with-open [rdr (reader "pages.txt")]
; 	  (doseq [line (line-seq rdr)]
; 	    (print(str/split line #" "))
; 			)
;   )
; )

(defn PrintInput []
	(def outMap {})
	(with-open [rdr (reader "pages.txt")]
	  (doseq [line (line-seq rdr)]
	  	; (print (type {(first (str/split line #" ")) (rest (str/split line #" "))}))
	  	(def outMap (merge outMap {(first (str/split line #" ")) (rest (str/split line #" "))}))
	  	; (print (type(first (str/split line #" "))) "\n")
	  	; (print(type (assoc outMap (first (str/split line #" ")) (rest (str/split line #" ")))))
	  	; (let outMap (merge outMap (first (str/split line #" ")) (rest (str/split line #" "))))
	  	; (print(assoc outMap (first (str/split line #" ")) (rest (str/split line #" "))))
		)
  )
  outMap
)
; (print(PrintInput))
(print(PrintInput))
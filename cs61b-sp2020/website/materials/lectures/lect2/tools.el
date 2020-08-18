;; This is an Emacs extension file.  
;; Load it into Emacs with 
;;     M-x load-file <RETURN> tools.el <RETURN>
;; (M-x == "Meta x" (hold down the Meta shift key and 'x' simultaneously)

(defun start-class (template-file package-name class-name)
  "Create file CLASS-NAME.java in the current directory from Template.java"
  (interactive "fTemplate: \nsPackage: \nsClass name: ")
  (find-file (concat class-name ".java"))
  (insert-file template-file)
  (goto-char (point-min))
  (while (search-forward "<CLASS>" nil t)
    (replace-match class-name t))
  (goto-char (point-min))
  (if (re-search-forward "<PACKAGE> *\n" nil t)
      (replace-match
       (if (string= package-name "")
	   ""
	   (concat "package " package-name ";\n\n"))
       t)))

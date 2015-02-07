package org.obolibrary.obo2owl;

import org.junit.Test;
import org.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class XrefIRITest extends OboFormatTestBasics {

    @Test
    public void testConversion() throws Exception {
        OWLOntology ontology = parseOWLFile("xrefIRItest.owl");
        OBODoc doc = convert(ontology);
        doc.getTermFrame("FOO:1");
        writeOBO(doc);
    }

    @Test
    public void testValidOutput() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        OWLOntology o = m
                .loadOntologyFromOntologyDocument(new IRIDocumentSource(
                        IRI.create("http://purl.bioontology.org/ontology/RXNO"),
                        new OBODocumentFormat(), null));
        for (OWLAxiom ax : o.getAxioms()) {
            String axstring = ax.toString();
            if (axstring.contains(":cg")) {
                System.out.println("XrefIRITest.testValidOutput() " + ax);
            }
        }
        OWLOntology o2 = roundTrip(o, new RDFXMLDocumentFormat());
        roundTrip(o2, new RDFXMLDocumentFormat());
    }
}

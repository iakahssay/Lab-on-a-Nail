<h1>Lab-on-a-Nail — Supplementary Materials</h1>

<p>
  This repository contains supplementary materials for the <strong>microfluidic nail biosensor research paper</strong>,
  including fabrication documentation, design files, and focus group study materials. These resources support system
  understanding, replication, and evaluation.
</p>

<hr />

<h2>Repository Contents</h2>

<h3>1. Nailytics Android App</h3>
<p>
  <strong>Nailytics/</strong><br />
  The folder containing the source code for the Nailytics mobile app. This includes the Kotlin files,
  XML layouts, resources, Gradle files, and Android project structure needed to open, build, and install Nailytics
  onto an Android device using Android Studio.
</p>
<p>
  Nailytics is the companion mobile prototype for the microfluidic nail biosensor system. The app guides users
  through selecting an analyte, connecting to a Nix color sensor, reading the hydrogel color sensing chamber,
  and viewing the interpreted analyte result.
</p>

<h3>2. Nailytics Android App Documentation</h3>
<p>
  <strong>Nailytics_ReadMe.md</strong><br />
  A dedicated README for the Nailytics Android app. Check this file for more information about the app structure,
  screen flow, Nix sensor workflow, analyte color chart logic, and how the app works.
</p>

<h3>3. Participant Study Material</h3>
<p>
  <strong>Nail It! Participants Guide and Deck.pdf</strong><br />
  A flyer provided to focus group participants that explains:
</p>
<ul>
  <li>The purpose of the research project</li>
  <li>Overview of the microfluidic nail system</li>
  <li>Instructions for activities completed during the focus group study</li>
</ul>
<p>This document reflects how the study and tasks were communicated to participants.</p>

<h3>4. Fabrication Documentation</h3>
<p>
  <strong>Paper Assay, Hydrogel, and Nail Fabrication Process.pdf</strong><br />
  Step-by-step instructions for constructing the system, including:
</p>
<ul>
  <li>Paper assay fabrication</li>
  <li>Colorimetric biosensor hydrogel fabrication</li>
  <li>Microfluidic nail fabrication</li>
  <li>Required materials and workflow</li>
</ul>
<p>This is the primary reference for reproducing the physical system.</p>

<h3>5. Microfluidic Nail Design Files</h3>
<ul>
  <li><strong>microfluidic nail.f3d</strong> — Editable Fusion 360 CAD model of the microfluidic nail.</li>
  <li><strong>microfluidic nail.stl</strong> — 3D-printable mesh file for direct fabrication.</li>
  <li><strong>nail 3d printing layout.form</strong> — Printing layout configuration for consistent manufacturing setup.</li>
</ul>

<h3>6. Paper Assay Design File</h3>
<p>
  <strong>Paper Assay Final Shape.studio3</strong><br />
  Vinyl cutting layout configuration for a consistent manufacturing setup of the paper-assay.
</p>

<h3>7. Nix Focus Attachment Design File</h3>
<p>
  <strong>nix_focus.studio3</strong><br />
  Vinyl cutting layout configuration used to cut a matte black paper focus attachment for the Nix device.
  The matte black paper is placed over the Nix device opening to narrow the visible measurement area, helping
  the Nix camera/measurement lens focus on the hydrogel color sensing chamber of the microfluidic fake nail.
</p>
<p>
  This attachment is useful because the Nix device's camera/measurement lens opening is larger in diameter
  than the hydrogel color sensing chamber. By physically limiting the opening, the Nix reading is better focused
  on the sensing chamber instead of surrounding nail or background material.
</p>

<hr />

<h2>Purpose</h2>
<ul>
  <li>Reproduction of the microfluidic nail biosensing system</li>
  <li>Inspection or modification of fabrication designs</li>
  <li>Understanding the Nailytics companion app and Nix sensing workflow</li>
  <li>Production of the matte black Nix focus attachment for more targeted chamber readings</li>
  <li>Transparency of study procedures and participant experience</li>
</ul>

<hr />

<h2>Basic Reproduction Workflow</h2>
<ol>
  <li>Follow fabrication steps in the fabrication process PDF</li>
  <li>Download and open the <code>Nailytics/</code> project folder in <code>Android Studio</code>, then install and run the app on an Android device.</li>
  <li>3D print the nail using the STL or FORM file (can also choose to modify the design using the F3D file)</li>
  <li>Prepare the paper assay, Nix focus attachment, and hydrogel components</li>
  <li>Assemble the full system</li>
  <li>Use the Nailytics app and Nix sensor to read the hydrogel color sensing chamber</li>
  <li>Use participant flyer if replicating the focus group study</li>
</ol>

<hr />

<h2>Recommended Software</h2>
<ul>
  <li>Autodesk Fusion 360 (for <code>.f3d</code>)</li>
  <li>Preform (FormLabs) (for <code>.form</code>) or another 3D slicing software (for <code>.stl</code>)</li>
  <li>Silhouette Studio (for paper assay production and the <code>nix_focus.studio3</code> Nix focus attachment)</li>
  <li>Android Studio (for opening the <code>Nailytics/</code> project folder and installing the app on an Android device)</li>
  <li>PDF viewer</li>
</ul>

<hr />

<h2>Citation</h2>
<p>Please cite the associated research paper when using these materials.</p>

<hr />

<h2>Contact</h2>
<p>For questions about fabrication or study replication, please contact the paper’s authors.</p>
